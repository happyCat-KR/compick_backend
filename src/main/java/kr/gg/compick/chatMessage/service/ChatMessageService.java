package kr.gg.compick.chatMessage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new NoSuchElementException("Match not found: " + matchId));

        List<ChatMessage> chatArr = chatMessageRepository.findByMatchIdOrderByCreatedAtDesc(match);

        // 반드시 초기화 필요
        List<ChatMessageDto> messages = new ArrayList<>();

        for (ChatMessage ca : chatArr) {
            // 이미 연관관계로 User 매핑되어 있으므로 userRepository로 다시 조회할 필요 없음
            User user = ca.getUserIdx();

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

}
