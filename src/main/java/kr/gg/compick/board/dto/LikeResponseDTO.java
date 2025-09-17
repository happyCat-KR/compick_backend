package kr.gg.compick.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponseDTO {
    private Long boardId;
    private boolean liked;   // 내가 누른 상태
    private Long likeCount;  // 총 좋아요 개수
}
