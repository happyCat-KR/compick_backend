package kr.gg.compick.match.dao;

import java.time.LocalDateTime;

public interface MatchCardProjection {
    Long getMatchId();
    String getLeagueNickname();
    String getSport();
    String getLeagueName();
    String getHomeTeamName();
    String getHomeTeamLogo();
    String getAwayTeamName();
    String getAwayTeamLogo();
    Integer getHomeScore();
    Integer getAwayScore();
    LocalDateTime getStartTime();
    String getMatchStatus();
}
