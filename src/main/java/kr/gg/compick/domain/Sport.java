package kr.gg.compick.domain;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "sport" )
@NoArgsConstructor @AllArgsConstructor 
@Builder @Getter @Setter
public class Sport {

    @Id
    @Column(name = "sport_id")
    private Long sportId;

    // 조회/식별용 코드 (예: "football", "baseball")
    @Column(name = "sport_code", nullable = false, length = 50, unique = true)
    private String sportCode;

    // 표시용 이름 (예: "축구", "야구")
    @Column(name = "sport_name", nullable = false, length = 100)
    private String sportName;

    @OneToMany(mappedBy = "sport", fetch = FetchType.LAZY)
    @Builder.Default
    private List<League> leagues = new ArrayList<>();
}
