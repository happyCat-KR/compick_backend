package kr.gg.compick.util;
import lombok.Data;
import java.util.Map;

@Data
public class KakaoProfile {
  private Long id;                               // provider_user_id
  private Map<String,Object> kakao_account;      // email 등
}
