package zerobase.hhs.reservation.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zerobase.hhs.reservation.domain.User;
import zerobase.hhs.reservation.dto.JwtToken;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final Key key;

    @Value("${jwt.access-expiration}")
    private long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${jwt.refresh-expiration}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    @Autowired
    public JwtTokenUtil(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken generateToken(User user) {
        long now = (new Date()).getTime();

        Date accessTokenExpired = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpired = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        Claims authClaim = Jwts.claims();
        authClaim.put("auth", user.getUserRole().name());

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setClaims(authClaim)
                .setExpiration(accessTokenExpired)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpired)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Access Token, Refresh Token 반환
        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
