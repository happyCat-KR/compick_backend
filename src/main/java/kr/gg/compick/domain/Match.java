package kr.gg.compick.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {
    @Id
    @Column(name = "match_id")
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private TeamInfo homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private TeamInfo awayTeam;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @ManyToOne
    @JoinColumn(name = "status_code")
    private MatchStatus status;
}
