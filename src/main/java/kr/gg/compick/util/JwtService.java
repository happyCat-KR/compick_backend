package kr.gg.compick.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtService {
    private final Key key;
    private final long accessMinutes;
    private final long refreshDays;

    public JwtService(Environment env) {
        String secret = env.getProperty("app.jwt.secret");
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessMinutes = Long.parseLong(env.getProperty("app.jwt.access-minutes", "15"));
        this.refreshDays = Long.parseLong(env.getProperty("app.jwt.refresh-days", "14"));
    }

    public String createAccess(Long userId, String role) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("role", role)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(accessMinutes * 60)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefresh(Long userId, String jti) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setId(jti)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(refreshDays * 24 * 60 * 60)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // kr.gg.compick.util.JwtService
    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

}
