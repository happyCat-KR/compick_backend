package kr.gg.compick.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "match_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchStatus {
    @Id
    private Integer code;

    private String description;
    private String type;
}
