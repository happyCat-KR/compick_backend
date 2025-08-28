package kr.gg.compick.match.dto;

import java.time.LocalDateTime;

import kr.gg.compick.domain.TeamInfo;
import kr.gg.compick.domain.League;
import kr.gg.compick.domain.RankSoccer;
import kr.gg.compick.match.util.TeamNameMapper;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SoccerRankDto {

    private Long rankId;
    private Long leagueId;
    private String leagueName;

    private String season;

    private Long teamId;
    private String teamName;
    private String teamLogo;

    private int playCount;
    private int wins;
    private int draws;
    private int losses;
    private int getGoal;
    private int losdGoal;
    private int goalCount;
    private int points;
    private int rank;
    private LocalDateTime updatedAt;

    public static SoccerRankDto fromEntity(RankSoccer e) {
        TeamInfo team = e.getTeamInfo();
        League league = e.getLeague();

        return SoccerRankDto.builder()
                .rankId(e.getRankId())
                .leagueId(league.getLeagueId())
                .leagueName(league.getLeagueName())
                .season(e.getSeason())
                .teamId(team.getTeamId())
                .teamName(TeamNameMapper.getKoreanName(team.getTeamName()))
                .teamLogo(team.getImageUrl())
                .playCount(e.getPlayCount())
                .wins(e.getWins())
                .draws(e.getDraws())
                .losses(e.getLosses())
                .getGoal(e.getGetGoal())
                .losdGoal(e.getLosdGoal())
                .goalCount(e.getGoalCount())
                .points(e.getPoints())
                .rank(e.getRank())
                .updatedAt(e.getUpdatedAt())
                .build();
    }
}