package kr.gg.compick.user.service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.gg.compick.domain.User;
import kr.gg.compick.domain.UserOauth;
import kr.gg.compick.domain.UserOauthId;
import kr.gg.compick.security.jwt.JwtTokenProvider;
import kr.gg.compick.user.dao.UserRepository;
import kr.gg.compick.user.dto.LoginDTO;
import kr.gg.compick.user.dto.UserRegistDTO;
import kr.gg.compick.userOauth.dao.UserOauthRepository;
import kr.gg.compick.util.NicknameGenerator;
import kr.gg.compick.util.ResponseData;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserOauthRepository userOauthRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String generateUnique(int maxTries) {
        for (int i = 0; i < maxTries; i++) {
            String nick = NicknameGenerator.normalize(NicknameGenerator.candidate());
            if (!userRepository.existsByUserNickname(nick))
                return nick;
        }
        return "사용자" + (100000 + ThreadLocalRandom.current().nextInt(900000));
    }

    @Transactional
    public ResponseData regist(UserRegistDTO userRegistDTO) {

        if (userRepository.existsByUserId(userRegistDTO.getUserId())) {
            return ResponseData.error(500, "이미 존재하는 아이디입니다.");
        }

        if (userRepository.existsByEmail(userRegistDTO.getEmail())) {
            return ResponseData.error(500, "이미 존재하는 이메일입니다.");
        }

        if (userRepository.existsByUserNickname(userRegistDTO.getUserName())) {
            return ResponseData.error(500, "이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .userId(userRegistDTO.getUserId())
                .password(userRegistDTO.getPassword())
                .userNickname(userRegistDTO.getUserName())
                .email(userRegistDTO.getEmail())
                .build();

        userRepository.save(user);

        return ResponseData.success();
    }

    @Transactional
    public ResponseData kakaoSignup(String email, String providerUserId, String provider) {
        UserOauthId id = new UserOauthId(provider, providerUserId);

        Optional<UserOauth> link = userOauthRepository.findById(id);
        if (link.isPresent()) {
            User user = link.get().getUser();
            String token = jwtTokenProvider.generateToken(user.getUserIdx());
            return ResponseData.success(token);
        }
        if (userRepository.existsByEmail(email)) {
            return ResponseData.error(500, "이미 존재하는 이메일 입니다.");
        }

        String nickname = generateUnique(20);
        User user = userRepository.save(User.builder()
                .email(email)
                .userNickname(nickname)
                .build());

        try {
            userOauthRepository.save(UserOauth.builder()
                    .id(id)
                    .user(user)
                    .build());

            String token = jwtTokenProvider.generateToken(user.getUserIdx());
            return ResponseData.success(token);
        } catch (DataIntegrityViolationException e) {
            return ResponseData.error(409, "연동 충돌이 발생했습니다.");

        }
    }

    @Transactional
    public ResponseData loginNomal(LoginDTO loginDTO) {
        return userRepository.findByUserId(loginDTO.getUserId())
                .map(user -> {
                    if (!loginDTO.getPassword().equals(user.getPassword())) {
                        return ResponseData.error(401, "비밀번호가 일치하지 않습니다.");
                    }

                    String token = jwtTokenProvider.generateToken(user.getUserIdx());
                    return ResponseData.success(token);

                })
                .orElse(ResponseData.error(404, "아이디가 존재하지 않습니다."));

    }

    @Transactional
    public ResponseData checkUserId(String userId) {
        return userRepository.existsByUserId(userId)
                ? ResponseData.error(500, "이미 존재하는 아이디입니다.")
                : ResponseData.success("사용 가능한 아이디입니다.");
    }

    @Transactional
    public ResponseData checkNickname(String nickname) {
        return userRepository.existsByUserNickname(nickname)
                ? ResponseData.error(500, "이미 존재하는 닉네임입니다.")
                : ResponseData.success("사용 가능한 닉네임입니다.");
    }

}
