package kr.gg.compick.user.service;

import org.springframework.stereotype.Service;

import kr.gg.compick.domain.User;
import kr.gg.compick.user.dao.UserRepository;
import kr.gg.compick.user.dto.LoginDTO;
import kr.gg.compick.user.dto.UserRegistDTO;
import kr.gg.compick.util.JwtTokenProvider;
import kr.gg.compick.util.ResponseData;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseData regist(UserRegistDTO userRegistDTO) {

        if (userRepository.existsByUserId(userRegistDTO.getUserId())) {
            return ResponseData.error(500, "이미 존재하는 아이디입니다.");
        }

        if (userRepository.existsByEmail(userRegistDTO.getEmail())) {
            return ResponseData.error(500, "이미 존재하는 이메일입니다.");
        }


        User user = User.builder()
                .userId(userRegistDTO.getUserId())
                .password(userRegistDTO.getPassword())
                .userName(userRegistDTO.getUserName())
                .email(userRegistDTO.getEmail())
                .build();

        userRepository.save(user);

        return ResponseData.success();
    }

    public ResponseData loginNomal(LoginDTO loginDTO) {
        return userRepository.findByUserId(loginDTO.getUserId())
                .map(user -> {
                    if(!loginDTO.getPassword().equals(user.getPassword())){
                        return ResponseData.error(401, "비밀번호가 일치하지 않습니다.");
                    }
                    
                    String token = jwtTokenProvider.generateToken(user.getUserIdx());
                    return ResponseData.success(token);
                        
                })
                .orElse(ResponseData.error(404, "아이디가 존재하지 않습니다."));

    }

    public ResponseData checkUserId(String userId) {
        return userRepository.existsByUserId(userId)
            ? ResponseData.error(500, "이미 존재하는 아이디입니다.")
            : ResponseData.success("사용 가능한 아이디입니다.");
    }

}
