package kr.gg.compick.domain.match;

import jakarta.persistence.*;
import kr.gg.compick.domain.TeamInfo;
import lombok.*;

@Entity
@Table(name = "match_score")
@IdClass(MatchScoreId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MatchScore {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Matches match;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamInfo team;

    @Column(name = "score", nullable = false)
    private int score;
}
