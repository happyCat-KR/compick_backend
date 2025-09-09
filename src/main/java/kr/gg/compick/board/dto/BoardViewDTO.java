package kr.gg.compick.board.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardViewDTO {
    private Long boardIdx;
    private String profileImage;
    private String matchtagName;
    private String userIdx;
    private String userId;
    private String content;
    private String fileUrls;
    private Integer likeCount;
    private Integer commentCount;

   
}
