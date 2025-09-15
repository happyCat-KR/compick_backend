package kr.gg.compick.user.dto;

import kr.gg.compick.domain.User;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserResponseDTO {
    private String userId;
    private String nickname;
    private String introduction;
    private String profileImg;

   
    public static UserResponseDTO fromEntity(User user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .nickname(user.getUserNickname())
                .introduction(user.getIntroduction())
                .profileImg(user.getProfileImage()) // ✅ DB 값 매핑
                .build();
    }
}
