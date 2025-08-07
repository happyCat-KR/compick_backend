package kr.gg.compick.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "user_name")
    private String userName;

    private String email;
    private String phone;
    private String password;

    @Column(name = "user_id")
    private String userId;

    private String bio;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "del_check")
    private boolean delCheck;

    @ManyToOne
    @JoinColumn(name = "del_user_idx")
    private User delUser;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private String alert1;
    private String alert2;
    private String alert3;
}
