package kr.gg.compick.board.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailViewDTO {
    private BoardViewDTO post;
    private List<BoardViewDTO> replies;
}