package kr.gg.compick.match.dto;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchCardDto {
    private Long matchId;
    private String sport;        
    private String leagueNickname;   
    private String leagueName;   
    private String leagueLogo;   // 리그 이미지 URL 추가
    private Long homeTeamId;  
    private String homeTeamName;
    private String homeTeamLogo;
    private int    homeScore;
    private Long awayTeamId;  
    private String awayTeamName;
    private String awayTeamLogo;
    private int    awayScore;
    private LocalDateTime startTime;
    private String matchStatus;
    
}