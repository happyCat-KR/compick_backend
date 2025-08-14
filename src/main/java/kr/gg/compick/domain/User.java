package kr.gg.compick.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "`USER`", uniqueConstraints = {
    @UniqueConstraint(name = "uk_user_email", columnNames = "email"),
    @UniqueConstraint(name = "uk_user_phone", columnNames = "phone"),
    @UniqueConstraint(name = "uk_user_userid", columnNames = "user_id"),
    @UniqueConstraint(name = "uk_user_usernum", columnNames = "user_num")
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
  private Long id;

  @Column(name = "user_name", length = 30)
  private String userName;

  @Column(name = "user_num", length = 36)
  private String userNum;

  @Column(name = "email", length = 100)
  private String email;

  @Column(name = "phone", length = 30)
  private String phone;

  @Column(name = "password", length = 255)
  private String password;

  @Column(name = "user_id", length = 255)
  private String userId;

  @Column(name = "bio", length = 255)
  private String bio;

  @Builder.Default
  @Column(name = "profile_image", length = 255, nullable = false)
  private String profileImage = "https://search2.kakaocdn.net/argon/656x0_80_wr/IloiKtAKa0F";

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
}
