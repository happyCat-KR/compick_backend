package kr.gg.compick.domain.match;

import jakarta.persistence.Column;
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
    @Column(name = "code")
    private Integer code;

    @Column(name = "description", nullable = false, length = 100)
    private String description;
    
    @Column(name = "type", length = 50)
    private String type;
}
