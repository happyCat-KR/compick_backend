package kr.gg.compick.match.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchTagDTO {    
    private Long matchId;
    private String sport;        
    private String leagueNickname;   
    private String leagueName;   
    private String leagueLogo;   // 리그 이미지 URL 추가
    private Number homeTeamId;  
    private String homeTeamName;
    private String homeTeamLogo;
    private int    homeScore;
    private Number awayTeamId;  
    private String awayTeamName;
    private String awayTeamLogo;
    private int    awayScore;
    private LocalDateTime startTime;
    private String matchStatus;
    private String leagueId;
}