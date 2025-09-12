package kr.gg.compick.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.gg.compick.chatMessage.dto.ChatMessageDto;
import kr.gg.compick.chatMessage.service.ChatMessageService;
import kr.gg.compick.domain.User;
import kr.gg.compick.security.UserDetailsImpl;
import kr.gg.compick.util.ResponseData;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/chat")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/view")
    public ResponseEntity<ResponseData> chatView(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @RequestParam("matchId") Long matchId) {
        User user = principal.getUser();
        List<ChatMessageDto> messages = chatMessageService.chatView(matchId);

        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("messages", messages);
        return ResponseEntity.ok(ResponseData.success(result));
    }

    @PostMapping("/regist")
    public ResponseEntity<ResponseData> chatRegist(
        @AuthenticationPrincipal UserDetailsImpl principal,
        @RequestParam("matchId") Long matchId,
        @RequestParam("content") String content 
    ) {
        User user = principal.getUser();
        ResponseData res = chatMessageService.chatRegist(matchId, user, content);
        return ResponseEntity.ok(res);
    }
    
}
