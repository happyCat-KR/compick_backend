package kr.gg.compick.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "`USER`", uniqueConstraints = {
    @UniqueConstraint(name = "uk_user_email", columnNames = "email"),
    @UniqueConstraint(name = "uk_user_userid", columnNames = "user_id"),
}, indexes = @Index(name = "idx_del_user_idx", columnList = "del_user_idx"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_idx")
  private Long userIdx;

  @Column(name = "user_nickname", length = 30)
  private String userNickname;

  @Column(name = "email", length = 100)
  private String email;

  @Column(name = "password", length = 255)
  private String password;

  @Column(name = "user_id", length = 255)
  private String userId;

  @Column(name = "bio", length = 255)
  private String bio;

 
  @Column(columnDefinition = "LONGTEXT")
  private String profileImage ;

  @Builder.Default
  @Column(name = "del_check", nullable = false)
  private boolean deleted = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "del_user_idx", foreignKey = @ForeignKey(name = "fk_user_deleted_by"))
  private User deletedBy;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(length = 500)
    private String introduction; 
}
