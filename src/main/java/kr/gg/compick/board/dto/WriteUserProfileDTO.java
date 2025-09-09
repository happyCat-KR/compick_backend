package kr.gg.compick.board.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WriteUserProfileDTO {
    private Long userIdx;
    private String userId;
    private String profileImage;
}