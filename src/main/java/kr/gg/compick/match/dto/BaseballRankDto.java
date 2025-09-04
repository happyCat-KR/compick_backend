// package kr.gg.compick.match.dto;

// import java.time.LocalDateTime;

// import kr.gg.compick.domain.TeamInfo;
// import kr.gg.compick.domain.League;
// import kr.gg.compick.domain.RankBaseball;
// import kr.gg.compick.domain.RankSoccer;
// import kr.gg.compick.match.util.TeamNameMapper;
// import lombok.Builder;
// import lombok.Getter;

// @Getter
// @Builder
// public class BaseballRankDto {

//     private Long rankId;
//     private Long leagueId;
//     private String leagueName;

//     private String season;

//     private Long teamId;
//     private String teamName;
//     private String teamLogo;

//     private int games;
//     private int wins;
//     private int losses;
//     private int ties;
//     private double winPct;       // 승률
//     private Double gamesBehind;  // 게임차
//     private int points;
//     private int rank;
//     private LocalDateTime updatedAt;

//     public static BaseballRankDto fromEntity(RankBaseball e) {
//         TeamInfo team = e.getTeam();
//         League league = e.getLeague();

//         return BaseballRankDto.builder()
//                 .rankId(e.getRankId())
//                 .leagueId(league.getLeagueId())
//                 .leagueName(league.getLeagueName())
//                 .season(e.getSeason())
//                 .teamId(team.getTeamId())
//                 .teamName(TeamNameMapper.getKoreanName(team.getTeamName()))
//                 .teamLogo(team.getImageUrl())
//                 .gamesBehind(e.getGamesBehind())
//                 .wins(e.getWins())
//                 .losses(e.getLosses())
//                 .ties(e.getTies())
//                 .winPct(e.getWinPct())
//                 .gamesBehind(e.getGamesBehind())
//                 .points(e.getPoints())
//                 .rank(e.getRankNo())
//                 .updatedAt(e.getUpdatedAt())
//                 .build();
//     }
// }