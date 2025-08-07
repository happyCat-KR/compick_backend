package kr.gg.compick.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board_like")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(BoardLikeId.class)
public class BoardLike {
    @Id
    @Column(name = "board_id")
    private Long boardId;

    @Id
    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "del_check")
    private boolean delCheck;

    @Column(name = "liked_at")
    private LocalDateTime likedAt;
}
