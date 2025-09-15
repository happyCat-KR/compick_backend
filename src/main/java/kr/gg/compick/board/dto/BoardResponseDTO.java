package kr.gg.compick.board.dto;


import java.time.LocalDateTime;
import java.util.List;

import kr.gg.compick.match.dto.MatchTagDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDTO{
    private Long boardId;
    private String userNickname;
    private String profileImage;
    private String title;
    private String content;
    private String fileUrl;
    private LocalDateTime createdAt;
    private String sport;
    private String league;
    private List<MatchTagDTO> matchtagName;
    private int viewCount;
    private long likeCount;
    private boolean likedByMe;

    public BoardResponseDTO(Long boardId,
                        String userNickname,
                        String profileImage,
                        String title,
                        String content,
                        String fileUrl,
                        LocalDateTime createdAt,
                        String sport,
                        String league,
                        int viewCount,
                        long likeCount,
                        boolean likedByMe) {
        this.boardId = boardId;
        this.userNickname = userNickname;
        this.profileImage = profileImage;
        this.content = content;
        this.fileUrl = fileUrl;
        this.createdAt = createdAt;
        this.sport = sport;
        this.league = league;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.likedByMe = likedByMe;
    }
    
}