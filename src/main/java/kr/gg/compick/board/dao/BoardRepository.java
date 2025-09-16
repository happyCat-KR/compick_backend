package kr.gg.compick.board.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import kr.gg.compick.board.dto.BoardResponseDTO;
import kr.gg.compick.domain.board.Board;


public interface BoardRepository extends JpaRepository<Board, Long> {

   @Modifying
    @Query("UPDATE Board b SET b.views = b.views + 1 WHERE b.boardId = :boardId")
    void incrementViews(@Param("boardId") Long boardId);

@Query("""
SELECT new kr.gg.compick.board.dto.BoardResponseDTO(
    bo.boardId,
    u.userNickname,
    u.profileImage,
    bo.title,
    bo.content,
    me.fileUrl,
    null, 
    bo.createdAt,
    c.sport.sportName,
    c.league.leagueNickname,
    COUNT(bl),
    false
)
FROM Board bo
JOIN bo.user u
JOIN bo.category c
LEFT JOIN bo.mediaList me
LEFT JOIN BoardLike bl ON bl.board = bo AND bl.delCheck = false
WHERE (:categoryIdx IS NULL OR LOWER(:categoryIdx) = 'al0' OR LOWER(c.categoryIdx) = LOWER(:categoryIdx))
GROUP BY bo.boardId, u.userNickname, u.profileImage, bo.title, bo.content,
         me.fileUrl, me.fileData, bo.createdAt, c.sport.sportName, c.league.leagueNickname
""")
List<BoardResponseDTO> findBoardsDynamic(@Param("categoryIdx") String categoryIdx);




     @Modifying
    @Transactional
    void deleteByUser_UserIdx(Long userIdx);
}
