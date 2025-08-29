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


    public enum TokenStatus {
        OK, EXPIRED, INVALID_SIGNATURE, MALFORMED, UNSUPPORTED, EMPTY, OTHER
    }

    public record TokenCheck(TokenStatus status, Long userId) {}

    public TokenCheck check(String token){
        if(token == null || token.isBlank()) return new TokenCheck(TokenStatus.EMPTY, null);
        try{
            var jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return new TokenCheck(TokenStatus.OK, Long.valueOf(jws.getBody().getSubject()));
        }catch(io.jsonwebtoken.ExpiredJwtException e){
            return new TokenCheck(TokenStatus.EXPIRED, Long.valueOf(e.getClaims().getSubject()));
        }catch(io.jsonwebtoken.security.SignatureException e) {
            return new TokenCheck(TokenStatus.INVALID_SIGNATURE, null);
        }catch(io.jsonwebtoken.MalformedJwtException e){
            return new TokenCheck(TokenStatus.MALFORMED, null);
        }catch(io.jsonwebtoken.UnsupportedJwtException e){
            return new TokenCheck(TokenStatus.UNSUPPORTED, null);
        }catch(IllegalArgumentException e){
            return new TokenCheck(TokenStatus.EMPTY, null);
        }catch(Exception e){
            return new TokenCheck(TokenStatus.OTHER, null);
        }
        
    }

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
