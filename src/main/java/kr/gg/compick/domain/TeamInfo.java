package kr.gg.compick.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

// 1. TeamInfo
@Entity
@Table(name = "team_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamInfo {
    @Id
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "image_url")
    private String imageUrl;
}
