// package kr.gg.compick.rank.dto;

// import kr.gg.compick.domain.rank.RankSoccer;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
// public class SoccerRankDto {
//     private Long rankId;
//     private String teamName;
//     private String teamLogo;
//     private int playCount;
//     private int wins;
//     private int draws;
//     private int losses;
//     private int getGoal;
//     private int losdGoal;
//     private int goalCount;
//     private int points;
//     private int rank;
//     private String season;
    
//     // 축구 승점 계산: 승 3점, 무승부 1점, 패 0점
//     public int calculatePoints() {
//         return (wins * 3) + (draws * 1) + (losses * 0);
//     }
    
//     // 승률 계산 (백분율)
//     public double getWinRate() {
//         if (playCount == 0) return 0.0;
//         return (double) wins / playCount * 100;
//     }
    
//     // 득실차 계산
//     public int calculateGoalDifference() {
//         return getGoal - losdGoal;
//     }
    
//     public static SoccerRankDto fromEntity(RankSoccer entity) {
//         SoccerRankDto dto = new SoccerRankDto();
//         dto.setRankId(entity.getRankId());
//         dto.setTeamName(entity.getTeamInfo().getTeamName());
//         dto.setTeamLogo(entity.getTeamInfo().getImageUrl());
//         dto.setPlayCount(entity.getPlayCount());
//         dto.setWins(entity.getWins());
//         dto.setDraws(entity.getDraws());
//         dto.setLosses(entity.getLosses());
//         dto.setGetGoal(entity.getGetGoal());
//         dto.setLosdGoal(entity.getLosdGoal());
//         dto.setGoalCount(entity.getGoalCount());
//         dto.setPoints(entity.getPoints());
//         dto.setRank(entity.getRank());
//         dto.setSeason(entity.getSeason());
//         return dto;
//     }
// }