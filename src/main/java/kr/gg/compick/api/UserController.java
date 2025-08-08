package kr.gg.compick.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.gg.compick.user.service.UserService;
import kr.gg.compick.user.dto.UserLoginDTO;
import kr.gg.compick.user.dto.UserRegistDTO;
import kr.gg.compick.util.ResponseData;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    final UserService userService;

    @PostMapping("/regist")
    public ResponseEntity<ResponseData> regist(@RequestBody UserRegistDTO userRegistDTO) {

        ResponseData responseData = userService.regist(userRegistDTO);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseData> regist(@RequestBody UserLoginDTO userLoginDTO) {

        ResponseData responseData = userService.login(userLoginDTO);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/check/userid")
    public ResponseEntity<ResponseData> userIdCheck(@RequestParam("userId") String userId) {
        ResponseData responseData = userService.userIdCheck(userId);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/check/email")
    public ResponseEntity<ResponseData> emailCheck(@RequestParam("email") String email) {
        ResponseData responseData = userService.emailCheck(email);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/check/phone")
    public ResponseEntity<ResponseData> phoneCheck(@RequestParam("phone") String phone) {
        ResponseData responseData = userService.phoneCheck(phone);
        return ResponseEntity.ok(responseData);
    }
}
