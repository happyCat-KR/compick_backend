package kr.gg.compick.user.service;

import kr.gg.compick.api.dto.LoginResponse;
import kr.gg.compick.domain.*;
import kr.gg.compick.user.dto.SignupRequest;
import kr.gg.compick.user.dao.UserRepository;
import kr.gg.compick.userOauth.dao.UserOauthRepository;
import kr.gg.compick.refreshToken.dao.RefreshTokenRepository;
import kr.gg.compick.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final UserOauthRepository oauthRepo;
    private final RefreshTokenRepository rtRepo;
    private final JwtService jwt;
    private final CookieUtil cookieUtil;
    private final KakaoClient kakao;
    private final NaverClient naver;
    private final Environment env;

    @Transactional
    public LoginResponse signupLocal(SignupRequest req, HttpServletResponse res) {
        if (userRepo.existsByLoginId(req.getLoginId()))
            throw new IllegalArgumentException("이미 존재하는 ID");
        if (req.getEmail() != null && userRepo.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("이미 존재하는 이메일");

        User u = User.builder()
                .loginId(req.getLoginId())
                .passwordHash(BCrypt.hashpw(req.getPassword(), BCrypt.gensalt()))
                .email(req.getEmail())
                .name(req.getName())
                .phone(req.getPhone())
                .profileImage("https://search2.kakaocdn.net/argon/656x0_80_wr/IloiKtAKa0F")
                .build();
        userRepo.save(u);

        return issueTokens(u, res);
    }

    @Transactional
    public LoginResponse kakaoCallback(String code, HttpServletResponse res) {
        OAuthToken t = kakao.exchangeCode(code);
        KakaoProfile p = kakao.me(t.getAccess_token());

        String provider = "kakao";
        String providerUserId = String.valueOf(p.getId());

        Optional<UserOauth> link = oauthRepo.findByIdProviderAndIdProviderUserId(provider, providerUserId);
        User user;
        if (link.isPresent()) {
            user = link.get().getUser();
        } else {
            user = new User();
            user.setEmail(null);
            user.setProfileImage("https://search2.kakaocdn.net/argon/656x0_80_wr/IloiKtAKa0F");
            userRepo.save(user);

            UserOauth uo = UserOauth.builder()
                    .id(new UserOauthId(provider, providerUserId))
                    .user(user)
                    .email(p.getKakao_account() == null ? null : (String) p.getKakao_account().get("email"))
                    .nickname(null)
                    .status("ACTIVE")
                    .connectedAt(LocalDateTime.now())
                    .build();
            oauthRepo.save(uo);
        }
        return issueTokens(user, res);
    }

    @Transactional
    public LoginResponse naverCallback(String code, String state, HttpServletResponse res) {
        OAuthToken t = naver.exchangeCode(code, state);
        NaverProfile p = naver.me(t.getAccess_token());

        String provider = "naver";
        String providerUserId = p.getResponse().get("id");

        Optional<UserOauth> link = oauthRepo.findByIdProviderAndIdProviderUserId(provider, providerUserId);
        User user;
        if (link.isPresent()) {
            user = link.get().getUser();
        } else {
            user = new User();
            user.setEmail(p.getResponse().get("email"));
            user.setProfileImage("https://search2.kakaocdn.net/argon/656x0_80_wr/IloiKtAKa0F");
            userRepo.save(user);

            UserOauth uo = UserOauth.builder()
                    .id(new UserOauthId(provider, providerUserId))
                    .user(user)
                    .email(p.getResponse().get("email"))
                    .nickname(p.getResponse().get("nickname"))
                    .status("ACTIVE")
                    .connectedAt(LocalDateTime.now())
                    .build();
            oauthRepo.save(uo);
        }
        return issueTokens(user, res);
    }

    private LoginResponse issueTokens(User u, HttpServletResponse res) {
        String access = jwt.createAccess(u.getId(), "USER");
        String jti = UUID.randomUUID().toString();
        String refresh = jwt.createRefresh(u.getId(), jti);

        // RefreshToken 저장
        log.info("Issuing refresh token for user: {}", u.getId());

        RefreshToken rt = RefreshToken.builder()
                .user(u)
                .jti(jti)
                .tokenHash(refresh) // 운영에선 해시로 저장 권장
                .issuedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(
                        Long.parseLong(env.getProperty("app.jwt.refresh-days", "14"))))
                .revoked(false)
                .build();
        rtRepo.save(rt);

        long maxAge = Long.parseLong(env.getProperty("app.jwt.refresh-days", "14")) * 24L * 60L * 60L;
        cookieUtil.setRefreshCookie(res, refresh, maxAge);

        return LoginResponse.builder().accessToken(access).userId(u.getId()).build();
    }

    // kr.gg.compick.user.service.AuthService
    @Transactional
    public LoginResponse refresh(String refreshJwt, HttpServletResponse res) {
        
        
        if (refreshJwt == null || refreshJwt.isBlank())
            throw new IllegalArgumentException("no refresh");

        var claims = jwt.parse(refreshJwt).getBody();
        Long userId = Long.valueOf(claims.getSubject());
        String jti = claims.getId();

        var saved = rtRepo.findByJti(jti).orElseThrow(() -> new IllegalArgumentException("invalid jti"));
        if (saved.isRevoked() || saved.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("expired/used");

        // 회전: 이전 jti 무효화
        saved.setRevoked(true);

        // 새 토큰 발급
        String newAccess = jwt.createAccess(userId, "USER");
        String newJti = UUID.randomUUID().toString();
        String newRefresh = jwt.createRefresh(userId, newJti);

        rtRepo.save(RefreshToken.builder()
                .user(User.builder().id(userId).build())
                .jti(newJti)
                .tokenHash(newRefresh) // 운영은 해시 권장
                .issuedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(
                        Long.parseLong(env.getProperty("app.jwt.refresh-days", "14"))))
                .revoked(false).build());

        long maxAge = Long.parseLong(env.getProperty("app.jwt.refresh-days", "14")) * 24L * 60L * 60L;
        cookieUtil.setRefreshCookie(res, newRefresh, maxAge);

        return LoginResponse.builder().accessToken(newAccess).userId(userId).build();
    }

}
