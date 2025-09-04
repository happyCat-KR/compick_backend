package kr.gg.compick.rank.dto;

import kr.gg.compick.domain.rank.RankBaseball;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseballRankDto {
    private Long rankId;
    private String teamName;
    private String teamLogo;
    private int playCount;
    private int wins;
    private int losses;  // 야구는 무승부 없음
    private int getGoal;
    private int losdGoal;
    private int points;  // 승률 기반 점수
    private int rank;
    private String season;
    
    // 야구 승률 계산
    public double getWinRate() {
        if (playCount == 0) return 0.0;
        return (double) wins / playCount * 100;
    }
    
    // 야구 승률 기반 점수 계산 (예: 승률 * 10)
    public int calculatePoints() {
        return (int) (getWinRate() * 10); // 승률을 10배로 변환
    }
    
    // 득실차 계산
    public int calculateRunDifference() {
        return getGoal - losdGoal;
    }
    
    public static BaseballRankDto fromEntity(RankBaseball entity) {
        BaseballRankDto dto = new BaseballRankDto();
        dto.setRankId(entity.getRankId());
        dto.setTeamName(entity.getTeamInfo().getTeamName());
        dto.setTeamLogo(entity.getTeamInfo().getImageUrl());
        dto.setPlayCount(entity.getGames());
        dto.setWins(entity.getWins());
        dto.setLosses(entity.getLosses());
        dto.setGetGoal((int) entity.getWinPct()); // 승률을 임시로 사용
        dto.setLosdGoal(0); // 야구는 실점 개념이 다름
        dto.setPoints(entity.getPoints());
        dto.setRank(entity.getRank());
        dto.setSeason(entity.getSeason());
        return dto;
    }
}