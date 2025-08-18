package kr.gg.compick.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.gg.compick.user.dto.LoginDTO;
import kr.gg.compick.user.dto.UserRegistDTO;
import kr.gg.compick.user.service.UserService;
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
    public ResponseEntity<ResponseData> loginNomal(@RequestBody LoginDTO loginDTO) {
        // 유저 등록 로직
        // userService.register(userRegistDTO);
        ResponseData res = userService.loginNomal(loginDTO);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/check/userid")
    public ResponseEntity<ResponseData> checkUserId(@RequestParam("userId") String userId) {
        ResponseData res = userService.checkUserId(userId);
        return ResponseEntity.ok(res);
    }
    
}
