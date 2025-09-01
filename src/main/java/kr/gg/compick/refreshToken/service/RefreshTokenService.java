package kr.gg.compick.refreshToken.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.gg.compick.domain.RefreshToken;
import kr.gg.compick.domain.User;
import kr.gg.compick.refreshToken.dao.RefreshTokenRepository;
import kr.gg.compick.refreshToken.dto.IssuedRefresh;
import kr.gg.compick.refreshToken.dto.RotatedTokens;
import kr.gg.compick.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

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

    @Transactional
    public RotatedTokens verifyAndRotate(String rtPlain, String userAgent, String ip) {
        // 1. jti 추출
        String jti = extrectJti(rtPlain);
        System.out.println("jti: "+ jti);

        // 2. DB 조회
        var rt = refreshTokenRepository.findByJti(jti)
                    .orElseThrow(() -> new RefreshException("RT_NOT_FOUND"));

        // 3. 상태 검사
        if(rt.isRevoked()) throw new RefreshException("RT_REVOKED");
        if(rt.getExpiresAt().isBefore(LocalDateTime.now())) throw new RefreshException("RT_EXPIRED");

        // 4. 해시 비교
        String hash = sha256(rtPlain);
        if (!hash.equals(rt.getTokenHash())) throw new RefreshException("RT_HASH_MISMATCH");

        // 5. 회전: 기존 RT revoke
        refreshTokenRepository.updateRevokedById(rt.getId());

        // 6. 새 rt 발급
        var issued = issue(rt.getUser(), userAgent, ip);

        // 7. 새 at 발급
        String newAt = jwtTokenProvider.generateToken(rt.getUser().getUserIdx());

        return new RotatedTokens(newAt, issued);
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
