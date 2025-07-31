package kr.gg.compick.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "team_info")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamInfo {

    @Id
    @Column(name = "team_id")
    private Integer teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "image_url", length = 500)
    private String imageUrl;
}
