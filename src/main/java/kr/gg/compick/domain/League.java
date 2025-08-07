package kr.gg.compick.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "league")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class League {
    @Id
    @Column(name = "league_id")
    private Long leagueId;

    @Column(name = "league_name")
    private String leagueName;

    @Column(name = "image_url")
    private String imageUrl;
}
