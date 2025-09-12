package kr.gg.compick.board.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardRegistDTO {
    private Long userIdx;
    private String sport;
    private String league;
    private String content;
    private List<String> matchtagName;
    private String image;

}