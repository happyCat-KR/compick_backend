package kr.gg.compick.api;

import kr.gg.compick.comment.dto.CommentResponseDTO;
import kr.gg.compick.comment.service.CommentService;
import kr.gg.compick.domain.User;
import kr.gg.compick.domain.board.Board;
import kr.gg.compick.domain.board.Comment;
import kr.gg.compick.security.UserDetailsImpl;
import kr.gg.compick.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

        private final CommentService commentService;
        private final UserRepository userRepository;

        // 댓글 작성 (로그인된 사용자만)
        @PostMapping("/{boardId}")
        public Comment writeComment(
                        @PathVariable("boardId") Long boardId,
                        @RequestParam("content") String content,
                        @AuthenticationPrincipal UserDetailsImpl principal) {
                String userId = principal.getUser().getUserId();
                User freshUser = userRepository.findByUserId(userId)
                                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

                Board board = new Board();
                board.setBoardId(boardId);

                return commentService.writeComment(board, freshUser, content);
        }

        // 대댓글 작성 (로그인된 사용자만)
        @PostMapping("/{boardId}/reply/{parentId}")
        public Comment writeReply(
                        @PathVariable("boardId") Long boardId,
                        @PathVariable("parentId") Long parentId,
                        @RequestParam("content") String content,
                        @AuthenticationPrincipal UserDetailsImpl principal) {
                String userId = principal.getUser().getUserId();
                User freshUser = userRepository.findByUserId(userId)
                                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

                Board board = new Board();
                board.setBoardId(boardId);

                Comment parent = new Comment();
                parent.setCommentId(parentId);

                return commentService.writeReply(board, freshUser, parent, content);
        }

        // 게시글별 댓글 조회 (최상위 댓글만)
        @GetMapping("/board/{boardId}")
        public List<CommentResponseDTO> getComments(@PathVariable("boardId") Long boardId) {
                Board board = new Board();
                board.setBoardId(boardId);

                return commentService.getComments(board)
                                .stream()
                                .map(CommentResponseDTO::fromEntity)
                                .toList();
        }

        // 특정 댓글의 대댓글 조회
        @GetMapping("/replies/{parentId}")
        public List<CommentResponseDTO> getReplies(@PathVariable("parentId") Long parentId) {
                Comment parent = new Comment();
                parent.setCommentId(parentId);

                return commentService.getReplies(parent)
                                .stream()
                                .map(CommentResponseDTO::fromEntity)
                                .toList();
        }

        // 로그인된 사용자가 작성한 댓글 조회
        @GetMapping("/me")
        public List<CommentResponseDTO> getMyComments(@AuthenticationPrincipal UserDetailsImpl principal) {
                String userId = principal.getUser().getUserId();
                User freshUser = userRepository.findByUserId(userId)
                                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

                return commentService.getCommentsByUser(freshUser)
                                .stream()
                                .map(CommentResponseDTO::fromEntity)
                                .toList();
        }
}
