package kr.gg.compick.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "match_vote")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UserMatchId.class)
public class MatchVote {
    @Id
    @Column(name = "user_idx")
    private Long userIdx;

    @Id
    @Column
    (name = "match_id")
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "vote_code")
    private VoteType voteCode;

    @Column(name = "voted_at")
    private LocalDateTime votedAt;
}