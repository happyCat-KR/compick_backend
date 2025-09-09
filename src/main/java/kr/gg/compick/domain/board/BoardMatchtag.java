package kr.gg.compick.domain.board;

import jakarta.persistence.*;
import kr.gg.compick.domain.user.Matchtag;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "board_matchtag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardMatchtag {

    @EmbeddedId
    private BoardMatchtagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("boardId")
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("matchtagIdx")
    @JoinColumn(name = "matchtag_idx")
    private Matchtag matchtag;

    @Column(name = "del_check")
    private boolean delCheck = false;

    @Column(name = "board_matchtag_at")
    private LocalDateTime boardMatchtagAt;

    @PrePersist
    protected void onCreate(){
        this.boardMatchtagAt = LocalDateTime.now();
    }
}