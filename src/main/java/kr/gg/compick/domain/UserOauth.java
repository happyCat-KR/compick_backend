package kr.gg.compick.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_oauth", indexes = @Index(name = "idx_user", columnList = "user_id"),
      uniqueConstraints = @UniqueConstraint(name = "uk_user_provider", columnNames = {
    "user_id", "provider" }))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOauth {
  @EmbeddedId
  private UserOauthId id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_oauth_user"))
  private User user;
}
