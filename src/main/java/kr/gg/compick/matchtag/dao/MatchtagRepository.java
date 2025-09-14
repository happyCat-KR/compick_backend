package kr.gg.compick.matchtag.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.gg.compick.domain.user.Matchtag;

@Repository
public interface MatchtagRepository extends JpaRepository<Matchtag, Long>{
    @Query("SELECT mt FROM Matchtag mt JOIN FETCH mt.match m WHERE mt.board.boardId = :boardId")
    List<Matchtag> findByBoardIdWithMatch(@Param("boardId") Long boardId);
}