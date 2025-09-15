package kr.gg.compick.board.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.gg.compick.domain.board.BoardLike;
import kr.gg.compick.domain.board.BoardLikeId;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikeId> {

    Optional<BoardLike> findByBoardIdAndUserIdx(Long boardId, Long userIdx);

    long countByBoardIdAndDelCheckFalse(Long boardId);
}
