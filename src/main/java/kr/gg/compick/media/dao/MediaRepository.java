package kr.gg.compick.media.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.gg.compick.domain.Media;
import kr.gg.compick.domain.board.Board;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long>{
    Optional<Media> findFirstByBoard_BoardId(Long boardId); 
    List<Media> findByBoard(Board board);
}