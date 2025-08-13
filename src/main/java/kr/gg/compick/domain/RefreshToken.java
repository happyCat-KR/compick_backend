package kr.gg.compick.domain;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name="refresh_tokens",
  indexes={
    @Index(name="idx_rt_user",         columnList="user_id"),
    @Index(name="idx_rt_user_expires", columnList="user_id, expires_at"),
    @Index(name="idx_rt_revoked",      columnList="revoked")
  },
  uniqueConstraints=@UniqueConstraint(name="uk_rt_jti", columnNames="jti"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RefreshToken {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch=FetchType.LAZY, optional=false)
  @JoinColumn(name="user_id", foreignKey=@ForeignKey(name="fk_rt_user"))
  private User user;

  @Column(length=36,  nullable=false) private String jti;       // UUID
  @Column(length=255, nullable=false) private String tokenHash;  // 원문 X
  @Column(nullable=false) private LocalDateTime issuedAt;
  @Column(nullable=false) private LocalDateTime expiresAt;
  @Column(nullable=false) private boolean revoked=false;
  @Column(length=255) private String userAgent;
  @Column(length=45)  private String ip;
}
