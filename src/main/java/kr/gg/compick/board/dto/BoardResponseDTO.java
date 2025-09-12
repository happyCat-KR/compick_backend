package kr.gg.compick.board.dto;


import java.time.LocalDateTime;

import kr.gg.compick.domain.board.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDTO{
        Category category;
        Long boardId;
        String userNickname;
        String userImage;
        String boardContent;
        String boardImage;
        LocalDateTime writeTime;
}