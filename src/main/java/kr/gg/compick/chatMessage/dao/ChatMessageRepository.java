package kr.gg.compick.chatMessage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.gg.compick.domain.ChatMessage;
import java.util.List;
import kr.gg.compick.domain.match.Matches;


@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{
        List<ChatMessage> findByMatches_MatchIdOrderByCreatedAtAsc(Long matchId);
}
