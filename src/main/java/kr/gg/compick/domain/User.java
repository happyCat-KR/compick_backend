package kr.gg.compick.domain;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "`user`") // MySQL 예약어라 백틱
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert // null 필드는 INSERT에서 제외 → DB DEFAULT 타게 함
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "user_name", nullable = false, length = 30)
    private String userName;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 30)
    private String phone;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "user_id", nullable = false, length = 255)
    private String userId;

    @Column(length = 255)
    private String bio; // 입력 안 하면 null

    // ✅ nullable=false 삭제 (Hibernate 사전 null체크 피함)
    @Column(name = "profile_image", length = 255)
    private String profileImage; // null이면 DB DEFAULT 적용

    @Builder.Default
    @Column(name = "del_check", nullable = false)
    private boolean delCheck = false;

    @ManyToOne
    @JoinColumn(name = "del_user_idx")
    private User delUser; // 기본 null

    // ✅ DB가 채우게 위임
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // 기본 null

    private String alert1;
    private String alert2;
    private String alert3;

    @PrePersist
    private void prePersist() {
        // 빈 문자열 들어오면 DB DEFAULT 타도록 null로 정리
        if (profileImage != null && profileImage.isBlank()) {
            profileImage = null;
        }
        // 들어오는 값들 공백 정리(선택)
        if (email != null) email = email.trim();
        if (phone != null) phone = phone.trim();
        if (userId != null) userId = userId.trim();
        if (userName != null) userName = userName.trim();
    }
}
