package kr.gg.compick.api;

import kr.gg.compick.comment.service.CommentService;
import kr.gg.compick.domain.User;
import kr.gg.compick.domain.board.Board;
import kr.gg.compick.domain.board.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{boardId}")
    public Comment writeComment(
            @PathVariable Long boardId,
            @RequestParam Long userId,
            @RequestParam String content
    ) {
        // TODO: 실제 구현에서는 Board, User를 Service/Repository로 조회해야 함
        Board board = new Board();
        board.setBoardId(boardId);

        User user = new User();
        user.setUserIdx(userId);

        return commentService.writeComment(board, user, content);
    }

    // 댓글 목록 조회
    @GetMapping("/{boardId}")
    public List<Comment> getComments(@PathVariable Long boardId) {
        Board board = new Board();
        board.setBoardId(boardId);

        return commentService.getComments(board);
    }
}
