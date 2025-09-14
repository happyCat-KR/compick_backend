package kr.gg.compick.api;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.gg.compick.board.dto.BoardRegistDTO;
import kr.gg.compick.board.dto.BoardResponseDTO;
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

    @PostMapping("path")
    public String postMethodName(@RequestBody String entity) {
        
        return entity;
    }
    

     // 게시글 작성
   @PostMapping(value = "/regist", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponseData<?>> boardRegist(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @RequestBody BoardRegistDTO dto
        )         
        throws IOException {
            User user = principal.getUser();
            dto.setUserIdx(user.getUserIdx());
            System.out.println("[보드 컨트롤러 도착]");

            ResponseData<?> responseData = boardService.boardRegist(dto);    
            System.out.println("[보드 response 컨트롤러 도착 ]"+responseData);   
            return ResponseEntity.ok(responseData);
        }


    @GetMapping("/{sport}/{league}/list")
    public ResponseEntity<List<BoardResponseDTO>> getBoardList(
            @RequestParam(required = false) String sport,
            @RequestParam(required = false) String league
    ) {
        // 값이 없으면 "all"로 변환
        String sportFilter = (sport == null || sport.isBlank()) ? "all" : sport;
        String leagueFilter = (league == null || league.isBlank()) ? "all" : league;

        List<BoardResponseDTO> boards = boardService.getBoardsList(sportFilter, leagueFilter);
        return ResponseEntity.ok(boards);
    }


   
}
