package kr.gg.compick.board.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import kr.gg.compick.domain.board.BoardLike;
import kr.gg.compick.domain.board.BoardLikeId;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikeId> {

    Optional<BoardLike> findByBoardIdAndUserIdx(Long boardId, Long userIdx);
    

    @Modifying
    @Transactional
    void deleteByUserIdx(Long userIdx);
    // User 엔티티의 userId 기준으로 삭제

    long countByBoardIdAndDelCheckFalse(Long boardId);
}
