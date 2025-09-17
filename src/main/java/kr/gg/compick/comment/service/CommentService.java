package kr.gg.compick.comment.service;

import kr.gg.compick.comment.dao.CommentRepository;
import kr.gg.compick.domain.User;
import kr.gg.compick.domain.board.Board;
import kr.gg.compick.domain.board.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 작성
    @Transactional
    public Comment writeComment(Board board, User user, String content) {
        Comment comment = Comment.builder()
                .board(board)
                .user(user)
                .content(content)
                .build();
        return commentRepository.save(comment);
    }

    // 대댓글 작성
    @Transactional
    public Comment writeReply(Board board, User user, Comment parent, String content) {
        Comment reply = Comment.builder()
                .board(board)
                .user(user)
                .parent(parent)  // ✅ 부모 댓글 연결
                .content(content)
                .build();
        return commentRepository.save(reply);
    }

    // 게시글별 댓글 조회 (최상위 댓글만)
    @Transactional(readOnly = true)
    public List<Comment> getComments(Board board) {
        return commentRepository.findByBoardAndParentIsNullOrderByCreatedAtAsc(board);
    }

    // 특정 댓글의 대댓글 조회
    @Transactional(readOnly = true)
    public List<Comment> getReplies(Comment parent) {
        return commentRepository.findByParentOrderByCreatedAtAsc(parent);
    }

    // 유저별 댓글 조회
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByUser(User user) {
        return commentRepository.findByUserOrderByCreatedAtDesc(user);
    }
}
