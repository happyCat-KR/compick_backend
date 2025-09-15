package kr.gg.compick.api;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.gg.compick.domain.User;
import kr.gg.compick.security.UserDetailsImpl;
import kr.gg.compick.user.dao.UserRepository;
import kr.gg.compick.user.dto.LoginDTO;
import kr.gg.compick.user.dto.PasswordCheckRequest;
import kr.gg.compick.user.dto.UserRegistDTO;
import kr.gg.compick.user.dto.UserResponseDTO;
import kr.gg.compick.user.dto.UserUpdateDTO;
import kr.gg.compick.user.service.UserService;
import kr.gg.compick.user.service.UserService.AuthTokens;
import kr.gg.compick.util.CookieUtil;
import kr.gg.compick.util.IPUtil;
import kr.gg.compick.util.ResponseData;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

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
        ResponseData<AuthTokens> res = userService.loginNomal(loginDTO, ip, userAgent);

        if (res.getCode() != 200 || res.getData() == null) {
            return ResponseEntity.status(res.getCode()).body(res);
        }

        var tokens = res.getData();
        var maxAge = Duration.between(LocalDateTime.now(), tokens.refresh().getExpiresAt());
        boolean isHttps = request.isSecure(); // 또는 profile로 분기

        response.addHeader("Set-Cookie", CookieUtil.deleteHttpOnlyCookie("rt", "/", isHttps));
        response.addHeader("Set-Cookie", CookieUtil.deleteHttpOnlyCookie("rt", "/api/auth", isHttps));
        response.addHeader("Set-Cookie", CookieUtil.buildHttpOnlyCookie(
                "rt", tokens.refresh().getToken(), maxAge, "/api/auth", isHttps));

        return ResponseEntity.ok(ResponseData.success(
                Map.of("accessToken", tokens.accessToken())));
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
        
        String userId = principal.getUser().getUserId();
        User freshUser = userRepository.findByUserId(userId)
                                   .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    UserResponseDTO dto = UserResponseDTO.fromEntity(freshUser);
        return ResponseEntity.ok(ResponseData.success(dto));
    }
   
    @PostMapping("/check-password")
    public ResponseEntity<Map<String, Boolean>> checkPassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody PasswordCheckRequest request) {

        boolean isMatch = userService.checkPassword(
                userDetails.getUserId(), // userId 전달
                request.getPassword()       // 입력한 비밀번호
        );

        Map<String, Boolean> response = new HashMap<>();
        response.put("success", isMatch);

        return ResponseEntity.ok(response);
    }

    /* 회원정보 수정 */
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails, // JWT 사용자
            @RequestBody UserUpdateDTO request
    ) {
        // 사용자 업데이트
        

         User updatedUser = userService.updateUser(userDetails.getUser().getUserId(), request);
         if (updatedUser != null) {
            UserResponseDTO responseDTO = UserResponseDTO.fromEntity(updatedUser);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "수정 실패"));
        }
    }


}
