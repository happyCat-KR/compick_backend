package kr.gg.compick.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.gg.compick.util.ResponseData;
import kr.gg.compick.verification.service.VerificationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class VerificationController {
    private final VerificationService verificationService;

    @PostMapping("/email/send")
    public ResponseEntity<ResponseData> sendEmailVerification(@RequestParam("email") String email){
        verificationService.sendVerificationCode(email, "signup");
        return ResponseEntity.ok(ResponseData.success("인증 이메일 발송 완료"));
    }

    @GetMapping("/email/verify")
    public ResponseEntity<ResponseData> verifyEmail(@RequestParam("email") String email,
                                                    @RequestParam("code") String code) {
        boolean verified = verificationService.verifyCode(email, code, "signup");
        if(verified){
            return ResponseEntity.ok(ResponseData.success("이메일 인증 성공"));
        }
        return ResponseEntity.badRequest().body(ResponseData.error(400, "이메일 인증 실패"));
    }
}
