package kr.gg.compick.board.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.gg.compick.board.dao.BoardRepository;
import kr.gg.compick.board.dto.BoardRegistDTO;
import kr.gg.compick.boardmatch.dao.BoardMatchtagRepository;
import kr.gg.compick.domain.Media;
import kr.gg.compick.domain.User;
import kr.gg.compick.domain.board.Board;
import kr.gg.compick.domain.board.BoardMatchtag;
import kr.gg.compick.domain.board.BoardMatchtagId;
import kr.gg.compick.domain.user.Matchtag;
import kr.gg.compick.matchtag.dao.MatchtagRepository;
import kr.gg.compick.media.dao.MediaRepository;
import kr.gg.compick.user.dao.UserRepository;
import kr.gg.compick.util.FileUploadUtil;
import kr.gg.compick.util.ResponseData;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final MatchtagRepository matchtagRepository;
    private final MediaRepository mediaRepository;
    private final BoardMatchtagRepository boardmatchtagRepository;
    private final BoardRepository boardRepository;

    
    @Transactional
    private Board boardInsert(BoardRegistDTO boardRegistDTO) {
        User user = userRepository.findById(boardRegistDTO.getUserIdx())
                .orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));

        Board parentBoard = null;
        if (boardRegistDTO.getParentIdx() != null) {
            parentBoard = boardRepository.findById(boardRegistDTO.getParentIdx())
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        }

        Board board = Board.builder()
                .user(user)
                .content(boardRegistDTO.getContent())
                .parentBoard(parentBoard)
                .build();

        return board;
    }
    @Transactional
    private Media mediaInsert(Board savedBoard, String url) {
        String extension = url.substring(url.lastIndexOf(".") + 1);
        
        Media media = Media.builder()
            .board(savedBoard)   // ✅ 필드명이 board라서 정상
            .fileUrl(url)
            .fileType(extension)
            .build();
        return media;
    }
     @Transactional
    private BoardMatchtag boardMatchtagInsert(Board savedBoard, String matchtagName) {

        Matchtag matchtag = matchtagRepository.findByMatchtagName(matchtagName)
                .orElseThrow(() -> new IllegalArgumentException("해쉬태그가 없습니다."));

        BoardMatchtagId boardMatchtagId = new BoardMatchtagId(
                savedBoard.getBoardId(), matchtag.getMatchtagIdx());

        BoardMatchtag boardMatchtag = BoardMatchtag.builder()
                .id(boardMatchtagId)
                .board(savedBoard)
                .matchtag(matchtag)
                .build();

        return boardMatchtag;
    }

    /*
     * 게시글 작성
     */
    @Transactional
    public ResponseData<?> boardRegist(BoardRegistDTO boardRegistDTO) throws IOException {

        
        // 1. board 테이블 객체 생성 및 반환.
        Board savedBoard = boardRepository.save(boardInsert(boardRegistDTO));

        List<String> savedImageUrls = FileUploadUtil.saveImages(boardRegistDTO.getPostImages(),
                boardRegistDTO.getUserIdx());

        for (String url : savedImageUrls) {
            // 2. media 테이블 객체 생성 및 반환
            mediaRepository.save(mediaInsert(savedBoard, url));
        }

        for (String matchtagName : boardRegistDTO.getMatchtagName()) {
            // 3. thread_hastag 테이블 객체 생성 및 반환
            boardmatchtagRepository.save(boardMatchtagInsert(savedBoard, matchtagName));

        }

        return ResponseData.success();

    }

    // @Transactional
    // public ResponseData PostWriteUserProfile(Long userIdx) {
    //     // userIdx 기준 존재 여부 확인
    //     boolean exists = userRepository.existsById(userIdx);
    //     if (!exists) {
    //         throw new IllegalArgumentException("존재하지 않는 유저입니다.");
    //     }

    //     // userIdx 기준 조회
    //     User user = userRepository.findById(userIdx)
    //             .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

    //     WriteUserProfileDTO dto = new WriteUserProfileDTO(
    //             user.getUserIdx(),    // PK (Long)
    //             user.getUserId(),     // 로그인 ID (String)
    //             user.getProfileImage()
    //     );

    //     return ResponseData.success(dto);
    // }

    // @Transactional
    // public ResponseData getRepliesView(Long parentIdx) {
    //     List<PostViewDTO> replies = postCustomRepository.findRepliesOfPost(parentIdx);
    //     return ResponseData.success(replies);
    // }

    // @Transactional
    // public ResponseData getDetailPost(Long postIdx) {
    //     PostViewDTO post = postCustomRepository.findDetailPost(postIdx); // postIdx 변수 없음
    //     List<PostViewDTO> replies = postCustomRepository.findRepliesOfPost(postIdx);
    //     PostDetailViewDTO detailView = new PostDetailViewDTO(post, replies);
    //     return ResponseData.success(detailView);
    // }
    // @Transactional
    // public ResponseData getSearchPostResult(String inputStr) {
    //     System.out.println("Search input: " + inputStr);
    //     List<PostViewDTO> hashtagResults = postCustomRepository.findPostsByHashtag(inputStr);
    //     List<PostViewDTO> contentResults = postCustomRepository.findPostsByContentKeyword(inputStr);

    //     // PostIdx 기준 중복 제거를 위해 Map 사용
    //     Map<Long, PostViewDTO> mergedMap = new LinkedHashMap<>();

    //     // hashtag 결과 먼저 넣기
    //     for (PostViewDTO t : hashtagResults) {
    //         mergedMap.put(t.getPostIdx(), t);
    //     }

    //     // content 결과 중복 없이 추가
    //     for (PostViewDTO t : contentResults) {
    //         mergedMap.putIfAbsent(t.getPostIdx(), t);
    //     }

    //     List<PostViewDTO> mergedList = new ArrayList<>(mergedMap.values());

    //     return ResponseData.success(mergedList);
    // }

    // @Transactional
    // public ResponseData getFollowingPosts(Long userIdx) {
    //     List<PostViewDTO> result = postCustomRepository.findFollowingPosts(userIdx);
    //     return ResponseData.success(result);
    // }

    // @Transactional
    // public ResponseData getTopPosts(Long userIdx) {
    //     List<PostViewDTO> result = postCustomRepository.findTopPostsByUser(userIdx);

    //     return ResponseData.success(result);
    // }

    // @Transactional
    // public ResponseData postRestore(Long postIdx) {
    //     Board post = postRepository.findById(postIdx)
    //             .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

    //     post.setDelCheck(false);
    //     board.setDeletedBy(null);
    //     board.setDeletedAt(null);

    //     return ResponseData.success();
    // }

    // @Transactional
    // public ResponseData boardDelete(Long boardIdx) {
    //     Board board = boardRepository.findById(boardIdx)
    //             .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

    //     board.setDelCheck(true);
    //     board.setDeletedBy(board.getUser());
    //     board.setDeletedAt(LocalDateTime.now());

    //     return ResponseData.success();
    // }

    // 매시간 정각에 실행
    // @Transactional
    // @Scheduled(cron = "0 * * * * *")
    // public void postCleanInsert() {
    //     LocalDateTime twoHoursAgo = LocalDateTime.now().minusMinutes(2);
    //     List<Board> boards = boardRepository.findAllForCleanup(twoHoursAgo);

    //     for (Board board : boards) {
    //         if (board.getContent().equals("삭제된 게시글"))
    //             continue;

    //         board.setContent("삭제된 게시글");

    //         boardMatchtagRepository.findAllByPostpostIdx(boards.getBoardIdx())
    //                 .forEach(postHashtag -> {
    //                     // 활동 기록 저장(삭제)
    //                     userActionService.regist(post.getUser(), postHashtag.getHashtag(), "post_delete");
    //                     // 해시태그 삭제
    //                     postHashtag.setDelCheck(true);
    //                 });

    //         mediaRepository.findAllByBoardBaordId(board.getBoardId())
    //                 .forEach(media -> media.setDelCheck(true));

    //         boardLikeRepository.findAllByBoardBaordId(board.getBoardId())
    //                 .forEach(like -> like.setDelCheck(true));
    //     }

    // }

    


    

   

}