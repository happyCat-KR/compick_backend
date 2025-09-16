package kr.gg.compick.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.gg.compick.board.dao.BoardLikeRepository;
import kr.gg.compick.domain.board.BoardLike;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;

    /**
     * 좋아요 토글 (있으면 취소, 없으면 등록)
     */
    @Transactional
    public boolean toggleLike(Long boardId, Long userIdx) {
        return boardLikeRepository.findByBoardIdAndUserIdx(boardId, userIdx)
                .map(like -> {
                    // 이미 존재하면 delCheck 토글
                    like.setDelCheck(!like.isDelCheck());
                    return !like.isDelCheck(); // true = 좋아요 활성 상태
                })
                .orElseGet(() -> {
                    // 없으면 새로 생성
                    BoardLike newLike = BoardLike.builder()
                            .boardId(boardId)
                            .userIdx(userIdx)
                            .delCheck(false)
                            .build();
                    boardLikeRepository.save(newLike);
                    return true; // 좋아요 활성 상태
                });
    }

    /**
     * 게시글 좋아요 수 조회
     */
    @Transactional(readOnly = true)
    public long countLikes(Long boardId) {
        return boardLikeRepository.countByBoardIdAndDelCheckFalse(boardId);
    }

    /**
     * 특정 사용자가 좋아요 눌렀는지 확인
     */
    @Transactional(readOnly = true)
    public boolean isLikedByUser(Long boardId, Long userIdx) {
        return boardLikeRepository.findByBoardIdAndUserIdx(boardId, userIdx)
                .map(like -> !like.isDelCheck())
                .orElse(false);
    }
}