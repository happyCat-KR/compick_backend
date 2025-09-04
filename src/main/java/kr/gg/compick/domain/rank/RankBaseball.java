package kr.gg.compick.domain.rank;

import java.time.LocalDateTime;
<<<<<<< HEAD:src/main/java/kr/gg/compick/domain/rank/RankBaseball.java

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.gg.compick.domain.League;
import kr.gg.compick.domain.TeamInfo;
=======
import jakarta.persistence.*;
>>>>>>> main:src/main/java/kr/gg/compick/domain/RankBaseball.java
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Entity
@Table(name = "rank_baseball")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankBaseball {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Long rankId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @Column(name = "season", nullable = false, length = 50)
    private String season;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamInfo team;

    // NULL 허용이므로 wrapper 사용
    @Column(name = "play_count")
    private Integer playCount;

    @Column(name = "wins")
    private Integer wins;

    @Column(name = "losses")
    private Integer losses;

    @Column(name = "ties")
    private Integer ties;

    @Column(name = "win_pct")
    private Double winPct;

    @Column(name = "games_behind")
    private Double gamesBehind;

    @Column(name = "points")
    private Integer points;

    @Column(name = "rank_no", nullable = false)
    private int rankNo;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
