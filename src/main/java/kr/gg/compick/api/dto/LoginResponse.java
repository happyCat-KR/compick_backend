package kr.gg.compick.api.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LoginResponse {
  private String accessToken;
  private Long   userId;  // USER.user_idx
}
