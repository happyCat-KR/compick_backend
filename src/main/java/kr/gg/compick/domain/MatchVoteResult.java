package kr.gg.compick.domain;
@Entity
@Table(name = "match_vote_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UserMatchId.class)
public class MatchVoteResult {
    @Id
    @Column(name = "user_idx")
    private Long userIdx;

    @Id
    @Column(name = "match_id")
    private Long matchId;

    @Column(name = "is_correct")
    private boolean isCorrect;

    @Column(name = "checked_at")
    private LocalDateTime checkedAt;
}