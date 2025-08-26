package kr.gg.compick.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "rank_baseball")
@Getter @Setter
public class RankBaseball {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Long rankId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @Column(nullable = false)
    private String season;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamInfo teamInfo;

    private int games;
    private int wins;
    private int losses;
    private int ties;

    @Column(name = "win_pct")
    private double winPct; // 승률

    @Column(name = "games_behind")
    private Double gamesBehind; // 게임차

    private int points;

    @Column(name = "rank_no", nullable = false)
    private int rank;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}