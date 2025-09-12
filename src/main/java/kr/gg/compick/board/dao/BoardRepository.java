package kr.gg.compick.board.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.gg.compick.board.dto.BoardResponseDTO;
import kr.gg.compick.domain.board.Board;


public interface BoardRepository extends JpaRepository<Board, Long> {

  // ✅ 전체 조회
 @Query("""
    SELECT new kr.gg.compick.board.dto.BoardResponseDTO(
        c,                       
        bo.boardId,              
        u.userNickname,          
        u.profileImage,          
        bo.content,              
        me.fileUrl,              
        bo.createdAt             
    )
    FROM Board bo
    JOIN Media me ON me.board = bo
    JOIN User u ON u.userIdx = bo.user.userIdx
    JOIN bo.category c
    WHERE (:sport IS NULL OR LOWER(c.sport.sportName) = LOWER(:sport))
      AND (:league IS NULL OR LOWER(c.league.leagueNickname) = LOWER(:league))
""")
List<BoardResponseDTO> findBoardsDynamic(@Param("sport") String sport,
                                         @Param("league") String league);

    @Query("""
        SELECT new kr.gg.compick.board.dto.BoardResponseDTO(
            c,
            bo.boardId,
            u.userNickname,
            u.profileImage,
            bo.content,
            me.fileUrl,
            bo.createdAt
        )
        FROM Board bo
        JOIN Media me ON me.board = bo
        JOIN User u ON u.userIdx = bo.user.userIdx
        JOIN bo.category c
    """)
    List<BoardResponseDTO> findBoardsWithCategory();
    

    
}
