package kr.gg.compick.domain;

import jakarta.persistence.*;
import lombok.*;

// 1. TeamInfo
@Entity
@Table(
    name = "team_info",
    indexes = {
        @Index(name = "ux_team_name", columnList = "team_name", unique = false)
    }
)
@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class TeamInfo {

    @Id
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "team_name", nullable = false, length = 150)
    private String teamName;

    @Column(name = "image_url")
    private String imageUrl;
}