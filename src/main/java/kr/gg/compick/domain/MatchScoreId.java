package kr.gg.compick.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.*;

@Data
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class MatchScoreId implements Serializable {
    private Long matchId;
    private Long teamId;
}