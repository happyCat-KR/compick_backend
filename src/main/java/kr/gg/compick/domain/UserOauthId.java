package kr.gg.compick.domain;


import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class UserOauthId implements Serializable {
  private String provider;        // VARCHAR(20)
  private String providerUserId;  // VARCHAR(180)
}
