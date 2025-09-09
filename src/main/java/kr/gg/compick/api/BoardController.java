package kr.gg.compick.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.gg.compick.board.dto.BoardRegistDTO;
import kr.gg.compick.board.service.BoardService;
import kr.gg.compick.util.ResponseData;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

     // 게시글 작성
    @PostMapping(value = "/regist")
    public ResponseEntity<ResponseData<?>> boardRegist(MultipartHttpServletRequest request) throws IOException {
        Long userIdx = Long.parseLong(request.getParameter("userIdx"));

        String parentIdxStr = request.getParameter("parentIdx");
        Long parentIdx = (parentIdxStr != null && !parentIdxStr.isBlank()) ? Long.parseLong(parentIdxStr) : null;

        String content = request.getParameter("content");
        if (content == null)
            content = "";

        String[] matchtagName = request.getParameterValues("matchtagName");
        List<String> matchtagList = (matchtagName != null) ? Arrays.asList(matchtagName) : List.of();

        List<MultipartFile> boardImages = request.getFiles("images");

        BoardRegistDTO boardRegistDTO = new BoardRegistDTO(userIdx, parentIdx, content, matchtagList, boardImages);

        ResponseData<?> responseData = boardService.boardRegist(boardRegistDTO);

        return ResponseEntity.ok(responseData);
    }
}
