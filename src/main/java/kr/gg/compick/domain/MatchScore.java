package kr.gg.compick.domain;

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
@Table(name = "match_score")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(MatchScoreId.class)
public class MatchScore {
    @Id
    @Column(name = "match_id")
    private Long matchId;

    @Id
    @Column(name = "team_id")
    private Long teamId;

    private int score;
}
