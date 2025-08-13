package kr.gg.compick.user.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SignupRequest {
  @NotBlank private String loginId;                 // USER.user_id
  @NotBlank @Size(min=8, max=64) private String password;
  @Email private String email;
  private String name;
  private String phone;
}
