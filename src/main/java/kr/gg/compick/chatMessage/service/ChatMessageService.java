package kr.gg.compick.chatMessage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.gg.compick.chatMessage.dao.ChatMessageRepository;
import kr.gg.compick.chatMessage.dto.ChatMessageDto;
import kr.gg.compick.domain.ChatMessage;
import kr.gg.compick.domain.User;
import kr.gg.compick.domain.match.Matches;
import kr.gg.compick.match.dao.MatchRepository;
import kr.gg.compick.user.dao.UserRepository;
import kr.gg.compick.util.ResponseData;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    final private ChatMessageRepository chatMessageRepository;
    final private UserRepository userRepository;
    final private MatchRepository matchRepository;

    public List<ChatMessageDto> chatView(Long matchId) {
        Matches match = matchRepository.findById(matchId)
                .orElseThrow(() -> new NoSuchElementException("Match not found"));

        List<ChatMessage> chatArr = chatMessageRepository.findByMatches_MatchIdOrderByCreatedAtAsc(matchId);

        // 반드시 초기화 필요
        List<ChatMessageDto> messages = new ArrayList<>();

        for (ChatMessage ca : chatArr) {
            // 이미 연관관계로 User 매핑되어 있으므로 userRepository로 다시 조회할 필요 없음
            User user = ca.getUser();

            ChatMessageDto dto = new ChatMessageDto(
                    ca.getMessageId(),
                    ca.getContent(),
                    user.getUserIdx(),
                    user.getUserNickname(),
                    ca.getCreatedAt());

            messages.add(dto);
        }

        return messages;

    }

    @Transactional
    public ResponseData chatRegist(Long matchId, User user, String content){
        try {
            System.out.println("matchId"+matchId);
            System.out.println("userIdx"+user.getUserIdx());
            System.out.println("content"+content);
            Matches matches = matchRepository.findById(matchId)
                .orElseThrow(() -> new NoSuchElementException("Match not found"));
            ChatMessage chatMessage = ChatMessage.builder()
                        .matches(matches)
                        .user(user)
                        .content(content)
                        .build();
            chatMessageRepository.save(chatMessage);
            return ResponseData.success();
        } catch(NoSuchElementException e){
            return ResponseData.error(500, "해당 경기를 찾을 수 없습니다.");
        } catch(Exception e){
            return ResponseData.error(500 , "채팅 등록 중 오류가 발생했습니다.");
        }

    }

}
