package kr.gg.compick.domain.board;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardLike implements Serializable {

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "user_idx")
    private Long userIdx;
}