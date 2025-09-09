package kr.gg.compick.board.dto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateDTO {
    private Long postIdx;
    private String content;
    private List<String> matchtagName;
    private List<MultipartFile> postImages;
}