package kr.gg.compick.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistDTO {
    private String userId;
    private String password;
    private String nickname;
    private String email;
}
