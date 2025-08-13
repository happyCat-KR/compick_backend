package kr.gg.compick.test.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import kr.gg.compick.test.NewJwtProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewAuthService {
    private final NewJwtProvider jwtProvider;

    public void login(String username, String password, HttpServletResponse response) {
        // 유저 검증 로직
        // ...

        //토큰 발급
        String accessToken = jwtProvider.createAccessToken(username);
        //String refreshToken = jwtProvider.createRefreshToken(username);

        //Header에 새 Access Token 설정
        response.setHeader("AUTH", accessToken);
        //return new TokenResponse(accessToken, refreshToken);

    }
}
