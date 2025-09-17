package kr.gg.compick.board.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.gg.compick.board.dao.BoardLikeRepository;
import kr.gg.compick.board.dao.BoardRepository;
import kr.gg.compick.board.dto.LikeResponseDTO;
import kr.gg.compick.domain.User;
import kr.gg.compick.domain.board.Board;
import kr.gg.compick.domain.board.BoardLike;
import kr.gg.compick.domain.board.BoardLikeId;
import kr.gg.compick.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

   @Transactional
    public LikeResponseDTO toggleLike(Long boardId, Long userIdx) {
        Optional<BoardLike> existing = boardLikeRepository.findByBoard_BoardIdAndUser_UserIdx(boardId, userIdx);

        boolean liked;
        if (existing.isPresent()) {
            BoardLike like = existing.get();
            if (!like.isDelCheck()) {
                like.setDelCheck(true);   // 좋아요 취소
                liked = false;
            } else {
                like.setDelCheck(false);  // 다시 좋아요
                liked = true;
            }
        } else {
            Board board = boardRepository.findById(boardId)
                    .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
            User user = userRepository.findById(userIdx)
                    .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

            BoardLike newLike = BoardLike.builder()
                    .board(board)
                    .user(user)
                    .delCheck(false)
                    .build();
            boardLikeRepository.save(newLike);
            liked = true;
        }

        Long likeCount = boardLikeRepository.countByBoard_BoardIdAndDelCheckFalse(boardId);
        return new LikeResponseDTO(boardId, liked, likeCount);
    }

    /**
     * ✅ 좋아요 개수 조회 (read-only)
     */
    @Transactional(readOnly = true)
    public Long countLikes(Long boardId) {
        return boardLikeRepository.countByBoard_BoardIdAndDelCheckFalse(boardId);
    }

    /**
     * ✅ 특정 유저가 누른 모든 좋아요 삭제
     */
    @Transactional
    public void deleteLikesByUser(Long userIdx) {
        boardLikeRepository.deleteByUser_UserIdx(userIdx);
    }
}
