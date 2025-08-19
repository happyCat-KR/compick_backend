package kr.gg.compick.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    private final Key key = Keys.hmacShaKeyFor("your-very-secret-long-secret-key-for-jwt-signing-1234567890".getBytes());
    private final long accessTokenValidityInMs = 1000 * 60 * 60L;

    public String generateToken(Long userIdx){
        Date now = new Date();

        return Jwts.builder()
                .setSubject(String.valueOf(userIdx))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidityInMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
