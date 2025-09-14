package kr.gg.compick.match.dto;
import java.time.LocalDateTime;

import kr.gg.compick.match.dao.MatchCardProjection;
import kr.gg.compick.match.util.TeamNameMapper;
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
    
    public MatchCardDto(MatchCardProjection p) {
        this.matchId = p.getMatchId();
        this.sport = p.getSport();
        this.leagueNickname = p.getLeagueNickname();
        this.leagueName = p.getLeagueName();
        this.leagueLogo = p.getLeagueLogo();
        this.homeTeamId = p.getHomeTeamId();
        this.homeTeamName = TeamNameMapper.getKoreanName(p.getHomeTeamName()); // 후처리
        this.homeTeamLogo = p.getHomeTeamLogo();
        this.awayTeamId = p.getAwayTeamId();
        this.awayTeamName = TeamNameMapper.getKoreanName(p.getAwayTeamName()); // 후처리
        this.awayTeamLogo = p.getAwayTeamLogo();
        this.homeScore = p.getHomeScore();
        this.awayScore = p.getAwayScore();
        this.startTime = p.getStartTime();
        this.matchStatus = p.getMatchStatus();
    }
}