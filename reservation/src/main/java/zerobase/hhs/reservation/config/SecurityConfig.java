package zerobase.hhs.reservation.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import zerobase.hhs.reservation.jwt.JwtProvider;
import zerobase.hhs.reservation.jwt.JwtTokenUtil;
import zerobase.hhs.reservation.jwt.LoginFilter;
import zerobase.hhs.reservation.service.RefreshService;
import zerobase.hhs.reservation.service.UserService;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshService refreshService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests.requestMatchers("/v3/**","/api/auth/**","/swagger-ui/**").permitAll()
                                .requestMatchers("/api/user/**").hasAnyAuthority("USER","PARTNER")
                                .requestMatchers("/api/partner/**").hasRole("PARTNER")
                                .requestMatchers("/api/review/**").hasAnyAuthority("USER","PARTNER")

                )
                .addFilterAfter(new LoginFilter(jwtProvider, jwtTokenUtil, userService,refreshService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
