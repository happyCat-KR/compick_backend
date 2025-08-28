package kr.gg.compick.match.dto;

import kr.gg.compick.domain.TeamInfo;
import kr.gg.compick.match.util.TeamNameMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamInfoDto {
    
    private Long teamId;
    private String teamName;
    private String imageUrl;
    
    /**
     * TeamInfo 엔티티를 TeamInfoDto로 변환 (한국어 팀명 포함)
     */
    public static TeamInfoDto fromEntity(TeamInfo teamInfo) {
        return TeamInfoDto.builder()
                .teamId(teamInfo.getTeamId())
                .teamName(TeamNameMapper.getKoreanName(teamInfo.getTeamName()))
                .imageUrl(teamInfo.getImageUrl())
                .build();
    }
    
    /**
     * TeamInfoDto를 TeamInfo 엔티티로 변환
     */
    public TeamInfo toEntity() {
        return TeamInfo.builder()
                .teamId(this.teamId)
                .teamName(this.teamName)
                .imageUrl(this.imageUrl)
                .build();
    }
}
