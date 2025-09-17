package kr.gg.compick.domain.board;
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Id; 

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import kr.gg.compick.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "board_like")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardLike implements Serializable {

    @EmbeddedId
    private BoardLikeId id;

    @MapsId("boardId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @MapsId("userIdx")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx", nullable = false)
    private User user;

    @Column(name = "del_check", nullable = false)
    private boolean delCheck = false;

    @Column(name = "liked_at", nullable = false, updatable = false)
    private LocalDateTime likedAt = LocalDateTime.now();

    // ✅ 빌더 커스터마이징 (id 자동 세팅)
    @Builder
    public BoardLike(Board board, User user, boolean delCheck) {
        this.board = board;
        this.user = user;
        this.delCheck = delCheck;
        if (board != null && user != null) {
            this.id = new BoardLikeId(board.getBoardId(), user.getUserIdx());
        }
    }
}
