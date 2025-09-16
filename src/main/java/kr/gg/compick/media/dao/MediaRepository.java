package kr.gg.compick.media.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.gg.compick.domain.Media;
import kr.gg.compick.domain.board.Board;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long>{
    List<Media> findAllByBoardBoardId(Long boardId); 
    List<Media> findByBoard(Board board);
    Media findFirstByBoard_BoardId(Long boardId);
}