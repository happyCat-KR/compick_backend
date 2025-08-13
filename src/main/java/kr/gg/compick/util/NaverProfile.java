package kr.gg.compick.util;
import lombok.Data;
import java.util.Map;

@Data
public class NaverProfile {
  private Map<String,String> response;           // id, email, nickname...
}
