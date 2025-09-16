package kr.gg.compick.domain.board;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardLikeId implements Serializable {
    private Long boardId;
    private Long userIdx;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardLikeId)) return false;
        BoardLikeId that = (BoardLikeId) o;
        return Objects.equals(boardId, that.boardId) &&
               Objects.equals(userIdx, that.userIdx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId, userIdx);
    }
}