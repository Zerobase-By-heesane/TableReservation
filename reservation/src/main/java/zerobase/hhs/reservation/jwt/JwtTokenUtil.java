package zerobase.hhs.reservation.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zerobase.hhs.reservation.Type.UserRole;
import zerobase.hhs.reservation.dto.JwtToken;
import zerobase.hhs.reservation.service.UserService;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private final Key key;

    @Value("${jwt.access-expiration}")
    private long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${jwt.refresh-expiration}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    @Autowired
    public JwtTokenUtil(@Value("${jwt.secret}")String secret){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken generateToken(Long userId){
        long now = (new Date()).getTime();

        Date accessTokenExpired = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpired = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setExpiration(accessTokenExpired)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Access Token, Refresh Token 반환
        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
