package kr.gg.compick.comment.dto;

import kr.gg.compick.domain.board.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDTO {
    private Long commentId;
    private String content;
    private String createdAt;
    private Long boardId;
    private String boardTitle;
    private String userId;
    private String userNickname;

    public static CommentResponseDTO fromEntity(Comment c) {
        return CommentResponseDTO.builder()
            .commentId(c.getCommentId())
            .content(c.getContent())
            .createdAt(c.getCreatedAt().toString())
            .boardId(c.getBoard().getBoardId())
            .boardTitle(c.getBoard().getTitle())
            .userId(c.getUser().getUserId())
            .userNickname(c.getUser().getUserNickname())
            .build();
    }
}
