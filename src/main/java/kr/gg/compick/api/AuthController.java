package kr.gg.compick.api;

import kr.gg.compick.api.dto.LoginResponse;
import kr.gg.compick.user.dto.SignupRequest;
import kr.gg.compick.user.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService auth;

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signup(@Valid @RequestBody SignupRequest req,
            HttpServletResponse res) {
        return ResponseEntity.ok(auth.signupLocal(req, res));
    }

    // kr.gg.compick.api.AuthController (대신 302로 보내기 옵션)
    @GetMapping("/kakao/callback")
    public void kakao(@RequestParam String code, HttpServletResponse res) throws IOException {
        auth.kakaoCallback(code, res); // Refresh 쿠키 세팅까지 수행
        res.sendRedirect(System.getenv().getOrDefault("FRONTEND_URL",
                "http://localhost:3000") + "/auth/done"); // SPA 라우트
    }

    @GetMapping("/naver/callback")
    public void naver(@RequestParam String code, @RequestParam String state, HttpServletResponse res)
            throws IOException {
        auth.naverCallback(code, state, res);
        res.sendRedirect(System.getenv().getOrDefault("FRONTEND_URL",
                "http://localhost:3000") + "/auth/done");
    }

    // kr.gg.compick.api.AuthController

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(
            HttpServletResponse res,
            @CookieValue(name = "refresh", required = false) String refreshToken) {

                log.info("Refresh token: {}", refreshToken);
        return ResponseEntity.ok(auth.refresh(refreshToken, res));
    }

}
