package kr.gg.compick.user.dto;

import kr.gg.compick.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateDTO {
    private String nickname;
    private String introduction;
    private String profileImg;
    
public UserUpdateDTO toDto(User user) {
    return UserUpdateDTO.builder()
            .nickname(user.getUserNickname())
            .introduction(user.getIntroduction())
            .profileImg(user.getProfileImage()) // 👈 여기 꼭 추가
            .build();
}
}
