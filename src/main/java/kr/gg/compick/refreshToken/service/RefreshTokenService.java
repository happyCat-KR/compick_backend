package kr.gg.compick.refreshToken.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.gg.compick.domain.RefreshToken;
import kr.gg.compick.domain.User;
import kr.gg.compick.refreshToken.dao.RefreshTokenRepository;
import kr.gg.compick.refreshToken.dto.IssuedRefresh;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${auth.refresh.ttl-days:14}")
    private long ttlDays;

    public IssuedRefresh issue(User user, String userAgent, String ip) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime exp = now.plusDays(ttlDays);
        String jti = UUID.randomUUID().toString();

        String tokenPlain = opaqueToken(jti);
        String tokenHash = sha256(tokenPlain);

        RefreshToken rt = RefreshToken.builder()
                .user(user)
                .jti(jti)
                .tokenHash(tokenHash)
                .issuedAt(now)
                .expiresAt(exp)
                .revoked(false)
                .userAgent(userAgent)
                .ip(ip)
                .build();
        refreshTokenRepository.save(rt);

        return new IssuedRefresh(tokenPlain, jti, exp);
    }

    private String extrectJti(String tokenPlain) {
        int i = tokenPlain.lastIndexOf('.');
        if (i < 0 || i == tokenPlain.length() - 1) {
            throw new RefreshException("RT_FORMAT_INVALID");
        }
        return tokenPlain.substring(i + 1);
    }

    private String opaqueToken(String jti) {
        byte[] random = new byte[48];
        new SecureRandom().nextBytes(random);
        String rnd = Base64.getUrlEncoder().withoutPadding().encodeToString(random);
        return rnd + "." + jti;
    }

    private String sha256(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return Base64.getEncoder().encodeToString(md.digest(s.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static class RefreshException extends RuntimeException {
        public RefreshException(String m) {
            super(m);
        }
    }

}
