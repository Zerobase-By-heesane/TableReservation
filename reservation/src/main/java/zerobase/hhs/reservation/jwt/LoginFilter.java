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
import zerobase.hhs.reservation.service.RefreshService;
import zerobase.hhs.reservation.service.UserService;

import java.io.IOException;
import java.util.Date;

@Slf4j
@AllArgsConstructor
public class LoginFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final RefreshService refreshService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Authorization 헤더에 토큰이 잘 들어가 있는지 확인
        // 없는 경우, return;
        String token = validateToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract Token
        // If Token is Invalid, return 401
        Claims claims = jwtProvider.validateToken(token);
        if (claims == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // If Access Token is Expired, validate Refresh Token
        if (claims.getExpiration().before(new Date())) {

            // Get User
            Long userId = Long.parseLong(claims.getSubject());
            User user = userService.getUser(userId);

            // Validate Refresh Token Through Cache
            String userRefreshToken = refreshService.cacheRefreshToken(userId);
            if (userRefreshToken == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // Extract User Information
            Claims userRefreshClaims = jwtProvider.validateToken(userRefreshToken);
            Date userRefreshExpiration = userRefreshClaims.getExpiration();

            // If Refresh Token is Expired, return status code 401
            if (userRefreshExpiration.before(new Date())) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            // Else Generate New Access Token
            else {
                String newAccessToken = jwtTokenUtil.generateToken(user).accessToken();
                // Set New Access Token to Response Header
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
