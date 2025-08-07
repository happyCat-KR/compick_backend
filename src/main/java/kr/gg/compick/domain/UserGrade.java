package kr.gg.compick.domain;

@Entity
@Table(name = "user_grade")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGrade {
    @Id
    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "total_votes")
    private int totalVotes;

    @Column(name = "correct_cnt")
    private int correctCnt;

    private String grade;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}