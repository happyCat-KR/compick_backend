package kr.gg.compick.board.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.gg.compick.board.dao.BoardRepository;
import kr.gg.compick.board.dto.BoardRegistDTO;
import kr.gg.compick.board.dto.BoardResponseDTO;
import kr.gg.compick.category.service.CategoryService;
import kr.gg.compick.domain.Media;
import kr.gg.compick.domain.User;
import kr.gg.compick.domain.board.Board;
import kr.gg.compick.domain.board.Category;
import kr.gg.compick.domain.match.Matches;
import kr.gg.compick.domain.user.Matchtag;
import kr.gg.compick.match.dao.MatchRepository;
import kr.gg.compick.match.dto.MatchTagDTO;
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
    private final MatchRepository matchRepository;
    private final BoardRepository boardRepository;
    private final CategoryService categoryService;
    
    @Transactional
    private Board boardInsert(BoardRegistDTO boardRegistDTO) {
        User user = userRepository.findById(boardRegistDTO.getUserIdx())
            .orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));

    // ✅ CategoryService에서 Category 엔티티 가져오기 (있으면 재사용, 없으면 생성)
        Category category = categoryService.getOrCreateCategory(
            boardRegistDTO.getSport(),
            boardRegistDTO.getLeague()
        );     

    return Board.builder()
            .user(user)
            .content(boardRegistDTO.getContent())
            .category(category)   // ✅ Category 엔티티로 조인
            .build();
            
    }    


    /*
     * 게시글 작성
     */
    @Transactional
    public ResponseData<?> boardRegist(BoardRegistDTO boardRegistDTO) throws IOException {  
        System.out.println("[보드 서비스 도착]");
        // 1. Board 저장
        Board savedBoard = boardRepository.save(boardInsert(boardRegistDTO));
        System.out.println("[보드 서비스 : board에 저장]");
        // 2. 이미지 URL 검증
        String savedImageUrl = null;
        if (boardRegistDTO.getImage() != null) {
            savedImageUrl = FileUploadUtil.saveImageUrl(boardRegistDTO.getImage());
        }

        if (savedImageUrl != null) {
            Media media = Media.builder()
                    .board(savedBoard)
                    .fileUrl(savedImageUrl)
                    .fileType(savedImageUrl.substring(savedImageUrl.lastIndexOf(".") + 1))
                    .build();
            mediaRepository.save(media);
        }
        System.out.println("[보드 ]");
        if (savedImageUrl != null) {
            // ✅ Media 엔티티 바로 생성
            String extension = "";
            System.out.println(savedImageUrl);
            int dotIndex = savedImageUrl.lastIndexOf(".");
            if (dotIndex > 0 && dotIndex < savedImageUrl.length() - 1) {
                extension = savedImageUrl.substring(dotIndex + 1);
        }   
        Media media = Media.builder()
                .board(savedBoard)
                .fileUrl(savedImageUrl)   // 바로 저장
                .fileType(extension)
                .build();
        mediaRepository.save(media);
    }
    
      // 3. Matchtag 저장
    List<MatchTagDTO> safeTags = boardRegistDTO.getMatchtagName() != null 
            ? boardRegistDTO.getMatchtagName() 
            : List.of();

    for (MatchTagDTO dto : safeTags) {
        Matches match = matchRepository.findById(dto.getMatchId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 matchId: " + dto.getMatchId()));

        Matchtag matchtag = Matchtag.builder()
                .board(savedBoard)
                .match(match)
                .build();

        matchtagRepository.save(matchtag);
    }

    return ResponseData.success();
}

    
    /* 게시글 조회 */
    public List<BoardResponseDTO> getBoardsList(String sport, String league) {
        return boardRepository.findBoardsDynamic(sport, league);
    }
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

    


    

   

