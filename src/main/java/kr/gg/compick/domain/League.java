package kr.gg.compick.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "league")
@NoArgsConstructor @AllArgsConstructor 
@Builder @Getter @Setter
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "league_id", nullable = false)
    private Long leagueId;

    @Column(name = "league_name")
    private String leagueName;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name ="league_nickname")
    private String leagueNickname;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "sport_id")
    private Sport sport;

}
