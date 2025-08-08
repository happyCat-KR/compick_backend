package kr.gg.compick.user.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.gg.compick.domain.User;
import kr.gg.compick.user.dao.UserRepository;
import kr.gg.compick.user.dto.UserLoginDTO;
import kr.gg.compick.user.dto.UserRegistDTO;
import kr.gg.compick.util.ResponseData;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository userRepository;

    @Transactional
    public ResponseData regist(UserRegistDTO userRegistDTO) {

        if (userRepository.existsByEmail(userRegistDTO.getEmail())) {
            return ResponseData.error(400, "이미 등록된 이메일입니다.");
        }

        if (userRepository.existsByPhone(userRegistDTO.getPhone())) {
            return ResponseData.error(400, "이미 등록된 전화번호입니다.");
        }

        if (userRepository.existsByUserId(userRegistDTO.getUserId())) {
            return ResponseData.error(400, "이미 사용 중인 사용자 ID입니다.");
        }

        User user = User.builder()
                .userId(userRegistDTO.getUserId())
                .userName(userRegistDTO.getUserName())
                .email(userRegistDTO.getEmail())
                .phone(userRegistDTO.getPhone())
                .password(userRegistDTO.getPassword())
                .build();

        userRepository.save(user);
        return ResponseData.success();
    }

    public ResponseData login(UserLoginDTO userLoginDTO){

        return userRepository.findByUserId(userLoginDTO.getUserId())
                .map(user -> {
                    if(!user.getPassword().equals(userLoginDTO.getPassword())){
                        return ResponseData.error(400, "비밀번호가 서로 다름!");
                    }
                    return ResponseData.success();
                })
                .orElse(ResponseData.error(400, "아이디가 존재하지 않습니다!"));
    }

    public ResponseData userIdCheck(String userId) {

        if (userRepository.existsByUserId(userId)) {
            return ResponseData.error(400, "이미 사용 중인 사용자 ID입니다.");
        }
        return ResponseData.success();
    }

    public ResponseData emailCheck(String email) {

        if (userRepository.existsByEmail(email)) {
            return ResponseData.error(400, "이미 등록된 이메일입니다.");
        }
        return ResponseData.success();
    }

    public ResponseData phoneCheck(String phone) {

        if (userRepository.existsByPhone(phone)) {
            return ResponseData.error(400, "이미 등록된 전화번호입니다.");
        }
        return ResponseData.success();
    }

}
