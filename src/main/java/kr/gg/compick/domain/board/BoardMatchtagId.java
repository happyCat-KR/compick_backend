package kr.gg.compick.domain.board;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardMatchtagId implements Serializable {

    private Long boardId;
    private Long matchtagIdx;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardMatchtagId)) return false;
        BoardMatchtagId that = (BoardMatchtagId) o;
        return Objects.equals(boardId, that.boardId) &&
               Objects.equals(matchtagIdx, that.matchtagIdx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId, matchtagIdx);
    }
}