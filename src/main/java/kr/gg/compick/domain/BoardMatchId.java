package kr.gg.compick.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class BoardMatchId implements Serializable {
    private Long boardId;
    private Long matchId;
}