package kr.gg.compick.domain.match;

import jakarta.persistence.*;
import kr.gg.compick.domain.League;
import kr.gg.compick.domain.TeamInfo;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "matches",
    indexes = {
        @Index(name = "ix_matches_league_time", columnList = "league_id, start_time"),
        @Index(name = "ix_matches_home_team", columnList = "home_team_id"),
        @Index(name = "ix_matches_away_team", columnList = "away_team_id")
    }
)
@NoArgsConstructor @AllArgsConstructor 
@Builder @Getter @Setter
public class Matches {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long matchId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "home_team_id")
    private TeamInfo homeTeam;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "away_team_id")
    private TeamInfo awayTeam;
    
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_code", referencedColumnName = "code")
    private MatchStatus status;
}