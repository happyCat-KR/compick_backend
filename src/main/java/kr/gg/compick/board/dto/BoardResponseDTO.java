package kr.gg.compick.board.dto;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import kr.gg.compick.domain.Media;
import kr.gg.compick.domain.board.Board;
import kr.gg.compick.match.dto.MatchTagDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDTO {
    private Long boardId;
    private String userNickname;
    private String profileImage;
    private String title;
    private String content;
    private String fileUrl;
    private String fileData;   // base64
    private LocalDateTime createdAt;
    private String sport;
    private String league;
    private List<MatchTagDTO> matchtagName;
    private Long likeCount;    // ✅ Long wrapper
    private Boolean likedByMe; // ✅ Boolean wrapper

    // JPQL DTO 매핑용 생성자
    public BoardResponseDTO(Long boardId,
                            String userNickname,
                            String profileImage,
                            String title,
                            String content,
                            String fileUrl,
                            String fileData,
                            LocalDateTime createdAt,
                            String sport,
                            String league,
                            Long likeCount,
                            Boolean likedByMe) {
        this.boardId = boardId;
        this.userNickname = userNickname;
        this.profileImage = profileImage;
        this.title = title;
        this.content = content;
        this.fileUrl = fileUrl;
        this.fileData = fileData;
        this.createdAt = createdAt;
        this.sport = sport;
        this.league = league;
        this.likeCount = likeCount;
        this.likedByMe = likedByMe;
    }

    // Entity -> Base64 변환
    public static String convertFileDataToBase64(Media media) {
        if (media != null && media.getFileData() != null) {
            return "data:" + media.getFileType() + ";base64," +
                   Base64.getEncoder().encodeToString(media.getFileData());
        }
        return null;
    }
    public static BoardResponseDTO fromEntity(Board board) {
    return new BoardResponseDTO(
            board.getBoardId(),
            board.getUser().getUserNickname(),
            board.getUser().getProfileImage(),
            board.getTitle(),
            board.getContent(),
            null,   // fileUrl → Media에서 처리
            null,   // fileData → Base64 변환 별도 처리
            board.getCreatedAt(),
            board.getCategory().getSport().getSportName(),
            board.getCategory().getLeague().getLeagueNickname(),
            0L,     // likeCount는 서비스에서 세팅
            false   // likedByMe도 서비스에서 세팅
    );
}

}
