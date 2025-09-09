package kr.gg.compick.domain.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import kr.gg.compick.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_matchtag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMatchtag {

    @EmbeddedId
    private UserMatchtagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userIdx")
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("matchtagIdx")
    @JoinColumn(name = "matchtag_idx")
    private Matchtag matchtag;

    @Column(name = "del_check")
    private boolean delCheck = false;

    @Column(name = "user_matchtag_at")
    private LocalDateTime userMatchtagAt;

    @PrePersist
    protected void onCreate(){
        this.userMatchtagAt = LocalDateTime.now();
    }
}