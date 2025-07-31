package kr.gg.compick.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    private Integer id;

    private String league;

    @Column(name = "home_team")
    private String homeTeam;

    @Column(name = "away_team")
    private String awayTeam;

    @Column(name = "start_time")
    private LocalDateTime startTime;
}
