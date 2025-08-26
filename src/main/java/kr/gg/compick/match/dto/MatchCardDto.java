package kr.gg.compick.match.dto;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MatchCardDto {
    public final Long matchId;
    public final String sport;        
    public final String leagueNickname;   
    public final String leagueName;   
    public final String homeTeamName;
    public final String homeTeamLogo;
    public final int    homeScore;
    public final String awayTeamName;
    public final String awayTeamLogo;
    public final int    awayScore;
    public final LocalDateTime startTime;
    public final String matchStatus;
}