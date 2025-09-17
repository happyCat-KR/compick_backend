package kr.gg.compick.board.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.gg.compick.board.dao.BoardLikeRepository;
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
    private final BoardLikeRepository boardLikeRepository;
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
            .category(category)
            .title(boardRegistDTO.getTitle())
            .content(boardRegistDTO.getContent())
            .build();
            
    }    
    @Transactional
    public BoardResponseDTO getBoardDetail(Long boardId, Long currentUserId) {
        // ✅ 조회수 증가
        boardRepository.incrementViews(boardId);

        // ✅ 게시글 엔티티 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글 없음"));

        // ✅ DTO 변환
        BoardResponseDTO dto = BoardResponseDTO.fromEntity(board);

        // ✅ 미디어 처리
        mediaRepository.findFirstByBoard_BoardId(boardId)
                .ifPresent(media -> dto.setFileData(BoardResponseDTO.convertFileDataToBase64(media)));

        // ✅ 좋아요 개수
        Long likeCount = boardLikeRepository.countByBoard_BoardIdAndDelCheckFalse(boardId);
        dto.setLikeCount(likeCount);
        System.out.println("[ 좋아요]: "+likeCount);

        // ✅ 내가 좋아요 눌렀는지 여부
        if (currentUserId != null) {
            boolean likedByMe = boardLikeRepository
                    .findByBoard_BoardIdAndUser_UserIdx(boardId, currentUserId)
                    .map(like -> !like.isDelCheck())
                    .orElse(false);
            dto.setLikedByMe(likedByMe);
        }

        return dto;
    }

    /*
     * 게시글 작성
     */
 @Transactional
public ResponseData<?> boardRegist(BoardRegistDTO dto) throws IOException {
    // 1. Board 저장
    Board newBoard = boardInsert(dto);
    Board savedBoard = boardRepository.save(newBoard);
    String fileUrl = "/uploads/posts/" + dto.getFile().getOriginalFilename();

    // 2. 이미지 저장
    if (dto.getFile() != null && !dto.getFile().isEmpty()) {
        Media media = Media.builder()
                .board(savedBoard)
                .fileName(dto.getFile().getOriginalFilename())
                .fileType(dto.getFile().getContentType())
                .fileData(dto.getFile().getBytes())
                .fileUrl(fileUrl)
                .delCheck(false)
                .build();

        mediaRepository.save(media);
    }

    return ResponseData.success("게시글 등록 성공", savedBoard.getBoardId());
}


    // 게시글 조회 
   public List<BoardResponseDTO> getBoardsList(String sport, String league) {
    // sport 매핑
    String sportdb = null;
    if ("soccer".equals(sport)) sportdb = "축구";
    else if ("baseball".equals(sport)) sportdb = "야구";
    else if ("mma".equals(sport)) sportdb = "MM";

    // league 매핑
    String leaguedb = null;
    if ("laliga".equals(league)) leaguedb = "1";
    else if ("ucl".equals(league)) leaguedb = "2";
    else if ("epl".equals(league)) leaguedb = "3";
    else if ("kbo".equals(league)) leaguedb = "4";
    else if ("ufc".equals(league)) leaguedb = "5";

    String categoryIdx = (sportdb != null ? sportdb : "") + (leaguedb != null ? leaguedb : "");

    // categoryIdx가 없으면 전체 조회
    List<BoardResponseDTO> boards;
    if (categoryIdx.isEmpty()) {
        boards = boardRepository.findAllBoards();
    } else {
        boards = boardRepository.findBoardsDynamic(categoryIdx);
    }

    // DTO 가공
    for (BoardResponseDTO dto : boards) {
        mediaRepository.findFirstByBoard_BoardId(dto.getBoardId())
                .ifPresent(media -> dto.setFileData(BoardResponseDTO.convertFileDataToBase64(media)));

        Long likeCount = boardLikeRepository.countByBoard_BoardIdAndDelCheckFalse(dto.getBoardId());
        dto.setLikeCount(likeCount);

        dto.setLikedByMe(null); // 로그인 안 한 경우
    }

    return boards;
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

    


    

   

