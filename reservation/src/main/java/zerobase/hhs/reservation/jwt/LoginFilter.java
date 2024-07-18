package zerobase.hhs.reservation.jwt;

import io.jsonwebtoken.Claims;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import zerobase.hhs.reservation.domain.User;
import zerobase.hhs.reservation.service.UserService;

import java.io.IOException;
import java.util.Date;

@Slf4j
@AllArgsConstructor
public class LoginFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = validateToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims claims = jwtProvider.validateToken(token);
        if (claims == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Date expiration = claims.getExpiration();

        // 만료된 경우
        if (expiration.before(new Date())) {
            User user = userService.getUser(Long.parseLong(claims.getSubject()));
            String userRefreshToken = user.getRefresh();

            Claims userRefreshClaims = jwtProvider.validateToken(userRefreshToken);
            Date userRefreshExpiration = userRefreshClaims.getExpiration();

            if (userRefreshExpiration.before(new Date())) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } else {
                String newAccessToken = jwtTokenUtil.generateToken(user.getId()).accessToken();
                response.setHeader("Authorization", "Bearer " + newAccessToken);
                token = newAccessToken;
            }
        }

        Authentication authentication = jwtProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private String validateToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(token) && token.startsWith("Bearer ")) {
            return token.split(" ")[1];
        }
        return null;
    }
}
