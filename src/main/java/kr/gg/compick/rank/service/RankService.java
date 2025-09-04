// package kr.gg.compick.rank.service;

// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Optional;

// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import kr.gg.compick.domain.League;
// import kr.gg.compick.domain.TeamInfo;
// import kr.gg.compick.domain.rank.RankSoccer;
// import kr.gg.compick.match.dao.LeagueRepository;
// import kr.gg.compick.match.dao.MatchCalenderRepository;
// import kr.gg.compick.match.dao.TeamInfoRepository;
// import kr.gg.compick.rank.dao.BaseballRankRepository;
// import kr.gg.compick.rank.dao.SoccerRankRepository;
// import kr.gg.compick.rank.dto.BaseballRankDto;
// import kr.gg.compick.rank.dto.SoccerRankDto;
// import lombok.RequiredArgsConstructor;



// @Service
// @RequiredArgsConstructor
// @Transactional(readOnly = true)
// public class RankService {

//     private final BaseballRankRepository baseballRankRepository;
//     private final SoccerRankRepository soccerRankRepository;
//     private final MatchCalenderRepository matchRepository;
//     private final LeagueRepository leagueRepository;
//     private final TeamInfoRepository teamInfoRepository;

//     public List<BaseballRankDto> getBaseballRanks(String sport, String league, String season) {
//         return baseballRankRepository.findBySportAndLeagueAndSeason(sport, league, season)
//                 .stream()
//                 .map(BaseballRankDto::fromEntity)
//                 .toList();
//     }

//     public List<SoccerRankDto> getSoccerRanks(String sport, String league, String season) {
//         return soccerRankRepository.findBySportAndLeagueAndSeason(sport, league, season)
//                 .stream()
//                 .map(SoccerRankDto::fromEntity)
//                 .toList();
//     }

//     // ========== 순위 업데이트 로직 ==========

//     /**
//      * 경기 결과에 따른 순위 업데이트
//      * 경기 결과가 입력되거나 변경될 때 호출
//      */
//     @Transactional
//     public void updateRankAfterMatch(Long matchId, Integer homeScore, Integer awayScore) {
//         // 경기 정보 조회
//         var match = matchRepository.findOneCardByMatchId(matchId);
//         if (match == null) {
//             throw new IllegalArgumentException("경기를 찾을 수 없습니다: " + matchId);
//         }

//         // 축구 경기가 아니면 순위 업데이트하지 않음
//         if (!"soccer".equalsIgnoreCase(match.getSport())) {
//             return;
//         }

//         // 리그 정보 조회 (leagueNickname으로 조회)
//         League league = leagueRepository.findByLeagueNickname(match.getLeagueNickname())
//                 .orElseThrow(() -> new IllegalArgumentException("리그를 찾을 수 없습니다: " + match.getLeagueNickname()));

//         // 팀 정보 조회 (팀명으로 조회)
//         TeamInfo homeTeam = teamInfoRepository.findByTeamName(match.getHomeTeamName())
//                 .orElseThrow(() -> new IllegalArgumentException("홈팀을 찾을 수 없습니다: " + match.getHomeTeamName()));
        
//         TeamInfo awayTeam = teamInfoRepository.findByTeamName(match.getAwayTeamName())
//                 .orElseThrow(() -> new IllegalArgumentException("원정팀을 찾을 수 없습니다: " + match.getAwayTeamName()));

//         // 현재 시즌 (예: 2024-25)
//         String currentSeason = getCurrentSeason();

//         // 홈팀 순위 업데이트
//         updateTeamRank(league, homeTeam, currentSeason, homeScore, awayScore, true);
        
//         // 원정팀 순위 업데이트
//         updateTeamRank(league, awayTeam, currentSeason, awayScore, homeScore, false);

//         // 전체 순위 재정렬
//         recalculateAllRanks(league, currentSeason);
//     }

//     /**
//      * 팀별 순위 정보 업데이트
//      */
//     private void updateTeamRank(League league, TeamInfo team, String season, 
//                                Integer teamScore, Integer opponentScore, boolean isHome) {
        
//         // 기존 순위 정보 조회 또는 새로 생성
//         RankSoccer rank = soccerRankRepository.findByLeagueAndTeamInfoAndSeason(league, team, season)
//                 .orElseGet(() -> {
//                     RankSoccer newRank = new RankSoccer();
//                     newRank.setLeague(league);
//                     newRank.setTeamInfo(team);
//                     newRank.setSeason(season);
//                     newRank.setPlayCount(0);
//                     newRank.setWins(0);
//                     newRank.setDraws(0);
//                     newRank.setLosses(0);
//                     newRank.setGetGoal(0);
//                     newRank.setLosdGoal(0);
//                     newRank.setGoalCount(0);
//                     newRank.setPoints(0);
//                     newRank.setRank(0);
//                     newRank.setUpdatedAt(LocalDateTime.now());
//                     return newRank;
//                 });

//         // 경기 수 증가
//         rank.setPlayCount(rank.getPlayCount() + 1);

//         // 득점, 실점 업데이트
//         rank.setGetGoal(rank.getGetGoal() + (teamScore != null ? teamScore : 0));
//         rank.setLosdGoal(rank.getLosdGoal() + (opponentScore != null ? opponentScore : 0));
//         rank.setGoalCount(rank.getGetGoal() - rank.getLosdGoal());

//         // 승패 판정 및 업데이트
//         if (teamScore != null && opponentScore != null) {
//             if (teamScore > opponentScore) {
//                 // 승리
//                 rank.setWins(rank.getWins() + 1);
//             } else if (teamScore.equals(opponentScore)) {
//                 // 무승부
//                 rank.setDraws(rank.getDraws() + 1);
//             } else {
//                 // 패배
//                 rank.setLosses(rank.getLosses() + 1);
//             }
//         }

//         // 승점 계산 (축구: 승 3점, 무승부 1점, 패 0점)
//         rank.setPoints((rank.getWins() * 3) + (rank.getDraws() * 1) + (rank.getLosses() * 0));
//         rank.setUpdatedAt(LocalDateTime.now());

//         // 저장
//         soccerRankRepository.save(rank);
//     }

//     /**
//      * 전체 순위 재정렬
//      */
//     private void recalculateAllRanks(League league, String season) {
//         List<RankSoccer> ranks = soccerRankRepository.findByLeagueAndSeasonOrderByPointsDescGoalCountDesc(league, season);
        
//         for (int i = 0; i < ranks.size(); i++) {
//             RankSoccer rank = ranks.get(i);
//             rank.setRank(i + 1); // 1부터 시작하는 순위
//             rank.setUpdatedAt(LocalDateTime.now());
//             soccerRankRepository.save(rank);
//         }
//     }

//     /**
//      * 현재 시즌 반환 (예: 2024-25)
//      */
//     private String getCurrentSeason() {
//         int currentYear = LocalDate.now().getYear();
//         int currentMonth = LocalDate.now().getMonthValue();
        
//         // 8월 이후는 다음 시즌으로 간주
//         if (currentMonth >= 8) {
//             return currentYear + "-" + String.valueOf(currentYear + 1).substring(2);
//         } else {
//             return (currentYear - 1) + "-" + String.valueOf(currentYear).substring(2);
//         }
//     }
// }