package kr.gg.compick.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "verification_codes", indexes = {
        @Index(name = "idx_verif_user_channel", columnList = "user_id, channel"),
        @Index(name = "idx_verif_destination", columnList = "destination"),
        @Index(name = "idx_verif_exp", columnList = "expires_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_verif_user"))
    private User user; // 가입 전이면 null

    @Column(length = 255, nullable = false)
    private String destination;
    @Column(name = "channel", nullable = false, columnDefinition = "enum('email','phone')")
    private String channel;
    @Column(name = "purpose", nullable = false, columnDefinition = "enum('signup','login','reset','change')")
    private String purpose;
    @Column(length = 255, nullable = false)
    private String codeHash;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    private LocalDateTime consumedAt;
    
    @Builder.Default
    @Column(name = "attempts", nullable = false)
    private Byte attempts = 0; // Byte는 MySQL TINYINT로 매핑

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public boolean isExpired() {
        return expiresAt != null && expiresAt.isBefore(LocalDateTime.now());
    }
}
