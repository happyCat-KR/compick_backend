package kr.gg.compick.comment.dao;

import kr.gg.compick.domain.board.Comment;
import kr.gg.compick.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글의 댓글 목록 조회
    List<Comment> findByBoardOrderByCreatedAtAsc(Board board);
}
