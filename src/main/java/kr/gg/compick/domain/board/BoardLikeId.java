package kr.gg.compick.domain.board;


import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
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