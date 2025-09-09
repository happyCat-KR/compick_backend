package kr.gg.compick.board.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.gg.compick.domain.board.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByUserUserIdx(Long userIdx);

    @Query("SELECT t FROM Board t WHERE t.delCheck = true AND t.deletedAt <= :threshold")
    List<Board> findAllForCleanup(@Param("threshold") LocalDateTime threshold);

}