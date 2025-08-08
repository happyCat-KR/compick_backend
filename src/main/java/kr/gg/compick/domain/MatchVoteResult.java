package kr.gg.compick.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "match_vote_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UserMatchId.class)
public class MatchVoteResult {
    @Id    
    @Column(name = "user_idx")
    private Long userIdx;

    @Id
    @Column(name = "match_id")
    private Long matchId;

    @Column(name = "is_correct")
    private boolean isCorrect;

    @Column(name = "checked_at")
    private LocalDateTime checkedAt;
}