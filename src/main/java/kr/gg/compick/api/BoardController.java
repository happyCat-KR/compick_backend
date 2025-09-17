package kr.gg.compick.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartRequest;

import kr.gg.compick.board.dto.BoardRegistDTO;
import kr.gg.compick.board.dto.BoardResponseDTO;
import kr.gg.compick.board.dto.LikeResponseDTO;
import kr.gg.compick.board.service.BoardLikeService;
import kr.gg.compick.board.service.BoardService;
import kr.gg.compick.domain.User;
import kr.gg.compick.security.UserDetailsImpl;
import kr.gg.compick.util.ResponseData;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardLikeService boardLikeService;

    @PostMapping("path")
    public String postMethodName(@RequestBody String entity) {
        
        return entity;
    }
    

   // 게시글 작성
    @PostMapping(value = "/regist", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<?>> boardRegist(
        @AuthenticationPrincipal UserDetailsImpl principal,
        @ModelAttribute BoardRegistDTO dto
    ) throws IOException {
        dto.setUserIdx(principal.getUser().getUserIdx());
        ResponseData<?> responseData = boardService.boardRegist(dto);
        return ResponseEntity.ok(responseData);
    }


  @PostMapping("/like/{boardId}")
    public ResponseEntity<LikeResponseDTO> toggleLike(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @PathVariable Long boardId
    ) {
        Long userIdx = principal.getUser().getUserIdx();
        LikeResponseDTO response = boardLikeService.toggleLike(boardId, userIdx);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/like/{boardId}")
    public ResponseEntity<Map<String, Object>> getLikeCount(@PathVariable Long boardId) {
        long count = boardLikeService.countLikes(boardId);
        return ResponseEntity.ok(Map.of("boardId", boardId, "likeCount", count));
    }


 @GetMapping("/list")
    public ResponseEntity<ResponseData> getBoardList(
            @RequestParam(required = false) String sport,
            @RequestParam(required = false) String league
    ) {
        List<BoardResponseDTO> boards = boardService.getBoardsList(sport, league);
        return ResponseEntity.ok(ResponseData.success(boards));
    }
    @GetMapping("/detail/{boardId}")
    public BoardResponseDTO getBoardDetail(@PathVariable Long boardId) {
        
        return boardService.getBoardDetail(boardId, null);
    }
   

    @PostMapping("/image/regist")
    public void imageRegist(MultipartRequest req) {
        
    }

}