package kr.gg.compick.domain.board;
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Id; 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Builder
@IdClass(BoardLikeId.class)
public class BoardLike implements Serializable {

    @Id
    @Column(name = "board_id")
    private Long boardId;

    @Id
    @Column(name = "user_idx")
    private Long userIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", referencedColumnName = "board_id", nullable = false)
    private Board board;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx", referencedColumnName = "user_idx", nullable = false)
    private User user;

    @Column(name = "del_check", nullable = false)
    private boolean delCheck = false;

    @Column(name = "liked_at", nullable = false, updatable = false)
    private LocalDateTime likedAt = LocalDateTime.now();
}