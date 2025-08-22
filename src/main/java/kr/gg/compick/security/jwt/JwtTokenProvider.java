package kr.gg.compick.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long accessTokenValidityInMs;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-exp-min}") long accessExpMin
    ){
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(bytes);
        this.accessTokenValidityInMs = accessExpMin * 60_000L;
    }

    public String generateToken(Long userIdx){
        Date now = new Date();

        return Jwts.builder()
                .setSubject(String.valueOf(userIdx))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidityInMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public Long getUserIdxFromToken(String token){
        return Long.valueOf(
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject()
        );
    }
}
