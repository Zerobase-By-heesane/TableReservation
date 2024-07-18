package zerobase.hhs.reservation.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import zerobase.hhs.reservation.jwt.JwtProvider;
import zerobase.hhs.reservation.jwt.JwtTokenUtil;
import zerobase.hhs.reservation.jwt.LoginFilter;
import zerobase.hhs.reservation.service.UserService;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

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
                        authorizeRequests -> authorizeRequests.requestMatchers("/**").permitAll()

                )
                .addFilter(new LoginFilter(jwtProvider, jwtTokenUtil, userService));

        return http.build();
    }
}
