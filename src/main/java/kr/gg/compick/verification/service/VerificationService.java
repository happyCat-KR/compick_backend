package kr.gg.compick.verification.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import kr.gg.compick.domain.VerificationCode;
import kr.gg.compick.user.dao.UserRepository;
import kr.gg.compick.util.EmailSender;
import kr.gg.compick.verification.dao.VerificationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final EmailSender emailSender;
    private final UserRepository userRepository;

    public void sendVerificationCode(String email, String purpose) {
        String code = generateCode();
        String hashCode = hashCode(code);

        VerificationCode verificationCode = VerificationCode.builder()
                .channel("email")
                .destination(email)
                .purpose(purpose)
                .codeHash(hashCode)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();
        
        verificationRepository.save(verificationCode);

        String link = "http://localhost:8080/api/auth/email/verify?email="
                + email + "&code=" + code;

        emailSender.send(email, "이메일 인증", "아래 링크를 클릭하여 인증을 완료하세요" + link);
    }

    public boolean verifyCode(String email, String code, String purpose){
        return verificationRepository.findTopByDestinationAndPurposeOrderByCreatedAtDesc(email, purpose)
                .filter(vc -> !vc.isExpired())
                .filter(vc -> matchesHash(code, vc.getCodeHash()))
                .map(vc -> {
                    vc.setConsumedAt(LocalDateTime.now());
                    verificationRepository.save(vc);

                    return true;
                }).orElse(false);

    }

    private String generateCode(){
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }

    private String hashCode(String code){
        return BCrypt.hashpw(code, BCrypt.gensalt());
    }

    private boolean matchesHash(String raw, String hashed) {
        return BCrypt.checkpw(raw, hashed);
    }


}
