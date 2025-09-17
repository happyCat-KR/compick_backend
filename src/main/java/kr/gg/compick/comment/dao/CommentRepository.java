package kr.gg.compick.comment.dao;

import kr.gg.compick.domain.board.Comment;
import kr.gg.compick.domain.board.Board;
import kr.gg.compick.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글의 댓글 목록 조회 (부모 댓글만, parent IS NULL)
    List<Comment> findByBoardAndParentIsNullOrderByCreatedAtAsc(Board board);

    // 특정 유저가 작성한 댓글 목록 조회
    List<Comment> findByUserOrderByCreatedAtDesc(User user);

    // 특정 댓글의 대댓글 조회
    List<Comment> findByParentOrderByCreatedAtAsc(Comment parent);
}
