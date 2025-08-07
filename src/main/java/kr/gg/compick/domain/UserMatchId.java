package kr.gg.compick.domain;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class UserMatchId implements Serializable {
    private Long userIdx;
    private Long matchId;
}