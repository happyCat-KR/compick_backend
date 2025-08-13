package kr.gg.compick.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="user_oauth",
  indexes=@Index(name="idx_user", columnList="user_id"),
  uniqueConstraints=@UniqueConstraint(name="uk_user_provider", columnNames={"user_id","provider"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserOauth {
  @EmbeddedId private UserOauthId id;

  @ManyToOne(fetch=FetchType.LAZY, optional=false)
  @JoinColumn(name="user_id", foreignKey=@ForeignKey(name="fk_user_oauth_user"))
  private User user;

  @Column(length=255) private String email;
  @Column(length=100) private String nickname;
  @Column(name="profile_image_url", length=500) private String profileImageUrl;
  @Column(columnDefinition="TEXT") private String scope;
  private LocalDateTime connectedAt;
  @Column(length=20, nullable=false) private String status="ACTIVE";

  @CreationTimestamp
  @Column(nullable=false, updatable=false) private LocalDateTime createdAt;
  @UpdateTimestamp private LocalDateTime updatedAt;
}
