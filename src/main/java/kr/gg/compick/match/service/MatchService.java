package kr.gg.compick.match.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.gg.compick.match.dao.MatchCardProjection;
import kr.gg.compick.match.dao.SoccerRankRepository;
import kr.gg.compick.match.dto.MatchCardDto;
import kr.gg.compick.match.dao.BaseballRankRepository;
import kr.gg.compick.match.dao.LeagueRepository;
import kr.gg.compick.match.dao.MatchCalenderRepository;
import kr.gg.compick.match.util.TeamNameMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchService {

    private final SoccerRankRepository soccerRankRepository;
    private final BaseballRankRepository baseballRankRepository;
    private final MatchCalenderRepository matchRepository;
    private final LeagueRepository leagueRepository;

    /** sport / league 별칭을 실제 PK로 변환 */
    private Long resolveLeagueId(String sport, String league) {
        // "all" / league==sport 같은 값은 여기서 처리하지 않고 호출부(getInRange)에서 처리
        if ("all".equalsIgnoreCase(league) || league.equalsIgnoreCase(sport)) {
            throw new IllegalArgumentException("개별 리그명이 필요합니다: " + sport + "/" + league);
        }
        
        // 약어 매핑을 먼저 시도 (epl, laliga, eucham 등)
        return leagueRepository.findIdBySportAndAbbreviation(sport, league)
                .orElseGet(() -> {
                    // 약어 매핑이 실패하면 기존 방식으로 시도
                    return leagueRepository.findIdBySportAndNickname(sport, league)
                            .orElseThrow(() -> new IllegalArgumentException("리그를 찾을 수 없습니다: " + sport + "/" + league));
                });
    }

    /** Projection → DTO 매핑 */
    private MatchCardDto toDto(MatchCardProjection p) {
        return new MatchCardDto(
            p.getMatchId(),
            p.getSport(),
            p.getLeagueNickname(),
            p.getLeagueName(),
            p.getLeagueLogo(),  // 리그 이미지 URL 추가
            TeamNameMapper.getKoreanName(p.getHomeTeamName()),  // 한국 통칭명으로 변환
            p.getHomeTeamLogo(),
            p.getHomeScore() == null ? 0 : p.getHomeScore(),
            TeamNameMapper.getKoreanName(p.getAwayTeamName()),  // 한국 통칭명으로 변환
            p.getAwayTeamLogo(),
            p.getAwayScore() == null ? 0 : p.getAwayScore(),
            p.getStartTime(),
            p.getMatchStatus()
        );
    }

    /** 상세 조회 */
    public MatchCardDto getDetail(String sport, String league, Long matchId) {
        // 모든 스포츠 요청이면 리그 ID 없이 조회
        if ("all".equalsIgnoreCase(sport)) {
            MatchCardProjection p = matchRepository.findOneCardByMatchId(matchId);
            if (p == null) {
                throw new IllegalArgumentException("경기를 찾을 수 없습니다: " + matchId);
            }
            return toDto(p);
        }
        
        // 스포츠 전체(=all) 요청이면 리그 ID 없이 조회
        if ("all".equalsIgnoreCase(league)) {
            MatchCardProjection p = matchRepository.findOneCardByMatchId(matchId);
            if (p == null) {
                throw new IllegalArgumentException("경기를 찾을 수 없습니다: " + matchId);
            }
            return toDto(p);
        }
        
        Long leagueId = resolveLeagueId(sport, league);
        MatchCardProjection p = matchRepository.findOneCard(leagueId, matchId);
        if (p == null) {
            throw new IllegalArgumentException("경기를 찾을 수 없습니다: " + matchId);
        }
        return toDto(p);
    }

    /** 임의 구간 조회: [start, end) */
    public List<MatchCardDto> getInRange(String sport, String league,
                                         LocalDateTime start, LocalDateTime end) {
        // ✅ 모든 스포츠 요청이면 전체 조회
        if ("all".equalsIgnoreCase(sport)) {
            return matchRepository.findAllMatchesInRange(start, end)
                    .stream()
                    .map(this::toDto)
                    .toList();
        }
        
        // ✅ 스포츠 전체(=all or league==sport) 요청이면 스포츠 단위로 한 번에 조회
        if ("all".equalsIgnoreCase(league) || league.equalsIgnoreCase(sport)) {
            return matchRepository.findInRangeBySport(sport, start, end)
                    .stream()
                    .map(this::toDto)
                    .toList();
        }

        // ✅ 개별 리그 조회
        Long leagueId = resolveLeagueId(sport, league);
        return matchRepository.findInRange(leagueId, start, end)
                .stream()
                .map(this::toDto)
                .toList();
    }

    /** 월 캘린더 그리드: 해당 월을 월~일로 꽉 채운 범위 [gridStart, gridEnd) */
    public List<MatchCardDto> getMonthlyGrid(String sport, String league, int year, int month) {
        LocalDate first = LocalDate.of(year, month, 1);
        LocalDate last  = first.plusMonths(1).minusDays(1);
        LocalDate gridStart = first.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate gridEnd   = last.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).plusDays(1); // exclusive
        return getInRange(sport, league, gridStart.atStartOfDay(), gridEnd.atStartOfDay());
    }

    /** 홈 화면용: 모든 스포츠와 모든 리그의 경기를 조회 */
    public List<MatchCardDto> getAllMatchesInRange(LocalDateTime start, LocalDateTime end) {
        return matchRepository.findAllMatchesInRange(start, end)
                .stream()
                .map(this::toDto)
                .toList();
    }

    /** 홈 화면용: 모든 스포츠와 모든 리그의 월별 캘린더 그리드 조회 */
    public List<MatchCardDto> getAllMatchesMonthlyGrid(int year, int month) {
        LocalDate first = LocalDate.of(year, month, 1);
        LocalDate last  = first.plusMonths(1).minusDays(1);
        LocalDate gridStart = first.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate gridEnd   = last.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).plusDays(1); // exclusive
        return getAllMatchesInRange(gridStart.atStartOfDay(), gridEnd.atStartOfDay());
    }
}
