package kr.gg.compick.api;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.gg.compick.user.service.UserService;
import kr.gg.compick.util.ResponseData;
import kr.gg.compick.verification.service.VerificationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class VerificationController {
    private final UserService userService;
    private final VerificationService verificationService;
    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.redirect-uri}")
    private String redirectUri;
    @Value("${app.front-base-url}")
    private String frontBaseUrl;

    @PostMapping("/email/send")
    public ResponseEntity<ResponseData> sendEmailVerification(@RequestParam("email") String email) {
        verificationService.sendEmail(email, "signup");
        return ResponseEntity.ok(ResponseData.success());
    }

    @GetMapping("/email/verify")
    public ResponseEntity<ResponseData> verifyEmail(@RequestParam("email") String email,
            @RequestParam("code") String code) {
        boolean verified = verificationService.verifyCode(email, code, "signup");
        if (verified) {
            return ResponseEntity.ok(ResponseData.success());
        }
        return ResponseEntity.badRequest().body(ResponseData.error(400, "실패"));
    }

    @GetMapping("/signup/kakao")
    public void beginKakaoSignup(HttpServletResponse res, HttpSession session) throws IOException {

        // CSRF공격 방지 토큰(인증 과정에서 요청 위변조 여부 확인하는 보안 파라미터)
        String state = UUID.randomUUID().toString();
        session.setAttribute("OAUTH_STATE", state);

        String authorizeUrl = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com/oauth/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("state", state)
                .queryParam("prompt", "login")
                .toUriString();

        res.sendRedirect(authorizeUrl);
    }

    @GetMapping("/kakao/callback")
    public void KakaoCallback(
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            HttpServletResponse res,
            HttpSession session) throws IOException {

        String saved = (String) session.getAttribute("OAUTH_STATE");
        session.removeAttribute("OAUTH_STATE");
        if (saved == null || !saved.equals(state)) {
            res.sendError(400, "Invalid state");
            return;
        }

        TokenRes kakaoToken = WebClient.create("https://kauth.kakao.com")
                .post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", clientId)
                        .with("redirect_uri", redirectUri)
                        .with("code", code))
                .retrieve()
                .onStatus(HttpStatusCode::isError, r -> r.bodyToMono(String.class)
                        .map(body -> new RuntimeException("Kakao token error: " + body)))
                .bodyToMono(TokenRes.class)
                .block();

        KakaoMe me = WebClient.create("https://kapi.kakao.com")
                .get()
                .uri("/v2/user/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + kakaoToken.access_token())
                .retrieve()
                .onStatus(HttpStatusCode::isError, r -> r.bodyToMono(String.class)
                        .map(body -> new RuntimeException("Kakao me error: " + body)))
                .bodyToMono(KakaoMe.class)
                .block();

        String providerUserId = me.id().toString();
        String email = me.kakao_account() != null ? me.kakao_account().email() : null;

        ResponseData responseData = userService.kakaoSignup(email, providerUserId, "kakao");

        String targetPath = (responseData.getCode() == 200) ? "/" : "/signup";

        UriComponentsBuilder b = UriComponentsBuilder
                .fromUriString(frontBaseUrl + targetPath)
                .queryParam("code", responseData.getCode());

        if (responseData.getCode() == 200 && responseData.getData() != null) {
            String token = (String) responseData.getData();
            b.fragment("token=" + java.net.URLEncoder.encode(token, java.nio.charset.StandardCharsets.UTF_8));
        }
        String url = b.build(true).toUriString();
        res.sendRedirect(url);

    }

}

record TokenRes(String access_token) {
}

record KakaoMe(Long id, KakaoAccount kakao_account) {
}

record KakaoAccount(String email) {
}
