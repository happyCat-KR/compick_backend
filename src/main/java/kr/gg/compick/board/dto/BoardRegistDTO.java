package kr.gg.compick.board.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.gg.compick.match.dto.MatchTagDTO;
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
    private String title;
    private String content;
    private List<MatchTagDTO> matchtagName;
    private MultipartFile file;

}