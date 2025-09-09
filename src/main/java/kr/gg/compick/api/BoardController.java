package kr.gg.compick.api;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.gg.compick.board.dto.BoardRegistDTO;
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

     // 게시글 작성
    @PostMapping(
    value = "/regist",
    consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResponseData<?>> boardRegist(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @RequestParam(value = "content", defaultValue = "") String content,
            @RequestParam(value = "matchtagName", required = false) List<String> matchtagList,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws IOException {

        User user = principal.getUser();
        long userIdx = user.getUserIdx();
        List<String> safeTags = (matchtagList != null) ? matchtagList : List.of();
        List<MultipartFile> safeImages = (images != null) ? images : List.of();

        BoardRegistDTO boardRegistDTO =
                new BoardRegistDTO(userIdx, content, safeTags, safeImages);

        ResponseData<?> responseData = boardService.boardRegist(boardRegistDTO);
        return ResponseEntity.ok(responseData);
    }

}
