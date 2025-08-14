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
    private String userName;
    private String email;
    private String phone;
}
