package kr.gg.compick.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistDTO {
    private String userName;
    private String email;
    private String phone;
    private String userId;
    private String password;
}
