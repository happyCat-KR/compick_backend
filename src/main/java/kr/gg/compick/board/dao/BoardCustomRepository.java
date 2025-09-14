package kr.gg.compick.board.dao;
import java.util.List;

import kr.gg.compick.board.dto.BoardViewDTO;

public interface BoardCustomRepository {
    List<BoardViewDTO> findTopBoardsByUser(Long userIdx);
    List<BoardViewDTO> findFollowingBoards(Long userIdx);

    List<BoardViewDTO> findBoardsByMatchtag(String matchtag);
    List<BoardViewDTO> findBoardsByContentKeyword(String userIdPrefix);
    BoardViewDTO findDetailBoard(Long boardIdx);
    // List<BoardViewDTO> findRepliesOfBoard(Long parentIdx);
}
