package kr.gg.compick.api;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.gg.compick.domain.User;
import kr.gg.compick.security.UserDetailsImpl;
import kr.gg.compick.user.dto.LoginDTO;
import kr.gg.compick.user.dto.UserRegistDTO;
import kr.gg.compick.user.service.UserService;
import kr.gg.compick.user.service.UserService.LoginTokens;
import kr.gg.compick.util.CookieUtil;
import kr.gg.compick.util.IPUtil;
import kr.gg.compick.util.ResponseData;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/regist")
    public ResponseEntity<ResponseData> registerUser(@RequestBody UserRegistDTO userRegistDTO) {
        // 유저 등록 로직
        // userService.register(userRegistDTO);
        ResponseData res = userService.regist(userRegistDTO);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login/normal")
    public ResponseEntity<ResponseData> loginNomal(
            @RequestBody LoginDTO loginDTO,
            HttpServletRequest request,
            HttpServletResponse response) {

        String ip = IPUtil.getClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        ResponseData res = userService.loginNomal(loginDTO, ip, userAgent);

        if (res.getCode() == 200) {
            LoginTokens tokens = (LoginTokens) res.getData();

            Duration maxAge = Duration.between(LocalDateTime.now(), tokens.refreshExpiresAt());
            boolean isHttps = request.isSecure(); // 또는 profile로 분기
            String cookie = CookieUtil.buildHttpOnlyCookie("rt", tokens.refreshToken(), maxAge, "/api/auth", isHttps);

            response.addHeader("Set-Cookie", cookie);
            return ResponseEntity.ok(ResponseData.success(
                    Map.of("accessToken", tokens.accessToken())));
        }

        return ResponseEntity.ok(res);

    }

    @GetMapping("/check/userid")
    public ResponseEntity<ResponseData> checkUserId(@RequestParam("userId") String userId) {
        ResponseData res = userService.checkUserId(userId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/check/nickname")
    public ResponseEntity<ResponseData> checkNickname(@RequestParam("nickname") String nickname) {
        ResponseData res = userService.checkNickname(nickname);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseData> info(@AuthenticationPrincipal UserDetailsImpl principal) {
        User user = principal.getUser();
        return ResponseEntity.ok(ResponseData.success(user));
    }

}
