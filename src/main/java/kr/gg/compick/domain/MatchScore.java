package kr.gg.compick.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "match_score",
    indexes = {
        @Index(name = "ix_score_match", columnList = "match_id"),
        @Index(name = "ux_score_match_team", columnList = "match_id, team_id", unique = true)
    }
)
@Data
@NoArgsConstructor @AllArgsConstructor @Builder
@IdClass(MatchScoreId.class)
public class MatchScore {

    @Id
    @Column(name = "match_id")
    private Long matchId;

    @Id
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "score", nullable = false)
    private int score;
}
