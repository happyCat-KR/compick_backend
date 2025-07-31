package kr.gg.compick.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "league")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class League {

    @Id
    private Integer id;

    @Column(name = "league_name")
    private String leagueName;

    @Column(name = "image_url", length = 500)
    private String imageUrl;
}
