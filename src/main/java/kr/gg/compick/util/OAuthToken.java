package kr.gg.compick.util;
import lombok.Data;

@Data
public class OAuthToken {
  private String access_token;
  private String token_type;
  private String refresh_token;
  private Long   expires_in;
}
