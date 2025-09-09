package kr.gg.compick.boardmatch.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.gg.compick.domain.board.BoardMatchtag;
import kr.gg.compick.domain.board.BoardMatchtagId;

@Repository
public interface BoardMatchtagRepository extends JpaRepository<BoardMatchtag, BoardMatchtagId>{
    List<BoardMatchtag> findAllByBoardBoardId(Long boarId);

}
