package kr.gg.compick.match.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.gg.compick.match.dao.MatchCardProjection;
import kr.gg.compick.match.dto.MatchCardDto;
import kr.gg.compick.match.dao.LeagueRepository;
import kr.gg.compick.match.dao.MatchCalenderRepository;
import kr.gg.compick.match.util.LeagueNameMapper;
import kr.gg.compick.match.util.TeamNameMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchService {
    private final MatchCalenderRepository matchRepository;
    private final LeagueRepository leagueRepository;
    
   

    /** Projection → DTO 매핑 */
    private MatchCardDto toDto(MatchCardProjection p) {
       Long homeId = p.getHomeTeamId() == null ? null : p.getHomeTeamId().longValue();
       Long awayId = p.getAwayTeamId() == null ? null : p.getAwayTeamId().longValue();

       System.out.println("homeTeamId: "+p.getHomeTeamId());


    return MatchCardDto.builder()
        .matchId(p.getMatchId())
        .sport(p.getSport())
        .leagueNickname(p.getLeagueNickname())
        .leagueName(p.getLeagueName())
        .leagueLogo(p.getLeagueLogo())

        .homeTeamId(homeId)
        .homeTeamName(TeamNameMapper.getKoreanName(p.getHomeTeamName()))
        .homeTeamLogo(p.getHomeTeamLogo())
        .homeScore(p.getHomeScore() == null ? 0 : p.getHomeScore())

        .awayTeamId(awayId)
        .awayTeamName(TeamNameMapper.getKoreanName(p.getAwayTeamName()))
        .awayTeamLogo(p.getAwayTeamLogo())
        .awayScore(p.getAwayScore() == null ? 0 : p.getAwayScore())

        .startTime(p.getStartTime())
        .matchStatus(p.getMatchStatus())
        .build();
}


    /** 상세 조회: matchId로 단건 */
    public MatchCardDto getDetail(String sport, String league, Long matchId) {
        System.out.println("[DETAIL] calling findOneCardByMatchId: " + matchId);
        
        MatchCardProjection p = matchRepository.findOneCardByMatchId(matchId);
        if (p == null) throw new IllegalArgumentException("경기를 찾을 수 없습니다: " + matchId);

        System.out.println("[DETAIL] proj.homeId=" + p.getHomeTeamId() + ", proj.awayId=" + p.getAwayTeamId());
        return toDto(p);
    }

    /** 임의 구간 조회: [start, end) */
   public List<MatchCardDto> getInRange(String sport, String league, LocalDateTime start, LocalDateTime end) {
    String s = v(sport);
    String l = v(league);

    if (isAll(s) && isAll(l)) {
        return matchRepository.findAllMatchesInRange(start, end)
                .stream().map(this::toDto).toList();
    }

    if (!isAll(s) && isAll(l)) {
        return matchRepository.findInRangeBySport(s, start, end)
                .stream().map(this::toDto).toList();
    }

    // 별칭 → DB 저장명 (지금 매퍼는 "epl" → "영국 프리미어 리그" 로 매핑)
    String mappedNickname = LeagueNameMapper.toDbNickname(l);

    Long leagueId = leagueRepository
        .findIdSmartIgnoringSpaces(s, l, mappedNickname)   // ← 메서드명 정확히 맞추기!
        .orElseGet(() -> 
            leagueRepository.findIdBySportAndNickname(s, mappedNickname) // 폴백 1: sport + 한국어 닉네임
                .orElseGet(() ->
                    leagueRepository.findIdBySportAndNickname(s, l)      // 폴백 2: sport + 원문 파라미터
                        .orElseThrow(() -> new IllegalArgumentException(
                            "리그를 찾을 수 없습니다: " + s + "/" + l + " (매핑: " + mappedNickname + ")"
                        ))
                )
        );

    return matchRepository.findInRange(leagueId, start, end)
            .stream().map(this::toDto).toList();
}
    /** 월 캘린더 그리드: 해당 월을 월~일로 꽉 채운 범위 [gridStart, gridEnd) */
    public List<MatchCardDto> getMonthlyGrid(String sport, String league, int year, int month) {
        LocalDate first = LocalDate.of(year, month, 1);
        LocalDate last  = first.plusMonths(1).minusDays(1);
        LocalDate gridStart = first.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate gridEnd   = last.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).plusDays(1); // exclusive
        return getInRange(sport, league, gridStart.atStartOfDay(), gridEnd.atStartOfDay());
    }

    /** 홈 화면용: 전체 범위 */
    public List<MatchCardDto> getAllMatchesInRange(LocalDateTime start, LocalDateTime end) {
        return matchRepository.findAllMatchesInRange(start, end)
                .stream().map(this::toDto).toList();
    }

    /** 홈 화면용: 월별 그리드 전체 */
    public List<MatchCardDto> getAllMatchesMonthlyGrid(int year, int month) {
        LocalDate first = LocalDate.of(year, month, 1);
        LocalDate last  = first.plusMonths(1).minusDays(1);
        LocalDate gridStart = first.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate gridEnd   = last.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).plusDays(1);
        return getAllMatchesInRange(gridStart.atStartOfDay(), gridEnd.atStartOfDay());
    }
    /* 홈 어웨이 맞대결 */
    public List<MatchCardDto> getHeadToHead(Long home, Long away) {
    return matchRepository.findHeadToHeadCards(home, away)
            .stream()
            .map(this::toDto)
            .toList();
    }

    /* 홈 최근대결 */
    // public List<MatchCardDto> getRecentHomeMatches(Long leagueId, Long teamId) {
    //     return matchRepository.findHomeMatch(leagueId, teamId)
    //             .stream().map(this::toDto).toList();
    // }

    // public List<MatchCardDto> getRecentAwayMatches(Long leagueId, Long teamId) {
    //     return matchRepository.findAwayMatch(leagueId, teamId)
    //             .stream().map(this::toDto).toList();
    // }

     /** 둘 다 한 번에 가져오는 편의 메서드 */
     
    public List<MatchCardDto> getRecentHomeMatch(Long leagueId, Long teamId) {
        return matchRepository.findHomeMatch(leagueId, teamId)
            .stream()
            .map(this::toDto)
            .toList();      
    }
      public List<MatchCardDto> getRecentAwayMatch(Long leagueId, Long teamId) {
        return matchRepository.findAwayMatch(leagueId, teamId)
            .stream()
            .map(this::toDto)
            .toList();      
    }


    // helpers
    private static boolean isAll(String v) { return v != null && v.equalsIgnoreCase("all"); }
    private static String v(String v) { return v == null ? "" : v.trim(); }
}
