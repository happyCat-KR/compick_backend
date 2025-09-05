package kr.gg.compick.api;

import kr.gg.compick.match.dao.LeagueRepository;
import kr.gg.compick.match.dto.MatchCardDto;
import kr.gg.compick.match.dto.TeamInfoDto;
import kr.gg.compick.match.service.MatchService;
import kr.gg.compick.match.service.TeamInfoService;
import kr.gg.compick.match.util.LeagueNameMapper;
import kr.gg.compick.util.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;


@Slf4j
@RestController
@RequestMapping("/api/match/{sport}/{league}")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final LeagueRepository leagueRepository;
    private final TeamInfoService teamInfoService;

    @PostConstruct
        public void init() {
            System.out.println("[MatchController] bean initialized: " + this.getClass().getName());
    }
    /**
     * 범위 조회 (FullCalendar 기본: start/end)
     * GET /api/match/{sport}/{league}?start=...&end=...
     */
    @GetMapping(params = {"start", "end"})
    public List<MatchCardDto> range(
            @PathVariable String sport,
            @PathVariable String league,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("end는 start보다 나중이어야 합니다.");
        }
        log.info("[RANGE] sport={}, league={}, start={}, end={}", sport, league, start, end);
        return matchService.getInRange(sport, league, start, end);
    }

    /**
     * 월별 경기 조회
     * GET /api/match/{sport}/{league}/monthly?year=2025&month=9
     */
    @GetMapping("/monthly")
    public ResponseEntity<List<MatchCardDto>> monthly(
            @PathVariable String sport,
            @PathVariable String league,
            @RequestParam int year,
            @RequestParam int month
    ) {
        log.info("[MONTHLY] sport={}, league={}, year={}, month={}", sport, league, year, month);
        List<MatchCardDto> result = matchService.getMonthlyGrid(sport, league, year, month);
       

    return ResponseEntity.ok()
            .header("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0")
            .header("Pragma", "no-cache")
            .header("Expires", "0")
            .body(result);
    }

    /**
     * 상세 조회
     * GET /api/match/{sport}/{league}/{matchId}
     */
    @GetMapping("/{matchId}")
    public MatchCardDto detail(
            @PathVariable String sport,
            @PathVariable String league,
            @PathVariable Long matchId
    ) {
        log.info("[DETAIL] sport={}, league={}, matchId={}", sport, league, matchId);
        MatchCardDto dto = matchService.getDetail(sport, league, matchId);
        log.info("[DETAIL] homeTeamId={}, awayTeamId={}", dto.getHomeTeamId(), dto.getAwayTeamId());
        return dto;
    }

    // ===== 팀 정보 관련 =====

    /**
     * 팀 ID로 팀 정보 조회
     * GET /api/match/{sport}/{league}/teams/{teamId}
     */
    @GetMapping("/teams/{teamId}")
    public ResponseEntity<ResponseData<TeamInfoDto>> getTeamById(
            @PathVariable String sport,
            @PathVariable String league,
            @PathVariable Long teamId
    ) {
        log.info("[TEAM:GET] sport={}, league={}, teamId={}", sport, league, teamId);
        return ResponseEntity.ok(teamInfoService.getTeamById(teamId));
    }

    /**
     * 팀명으로 팀 검색
     * GET /api/match/{sport}/{league}/teams/search?keyword=...
     */
    @GetMapping("/teams/search")
    public ResponseEntity<ResponseData<List<TeamInfoDto>>> searchTeamsByName(
            @PathVariable String sport,
            @PathVariable String league,
            @RequestParam String keyword
    ) {
        log.info("[TEAM:SEARCH] sport={}, league={}, keyword={}", sport, league, keyword);
        return ResponseEntity.ok(teamInfoService.searchTeamsByName(keyword));
    }

    /**
     * 팀명 업데이트 (한국어 팀명으로 패치)
     * PATCH /api/match/{sport}/{league}/teams/{teamId}/name
     */
    @PatchMapping("/teams/{teamId}/name")
    public ResponseEntity<ResponseData<TeamInfoDto>> updateTeamName(
            @PathVariable String sport,
            @PathVariable String league,
            @PathVariable Long teamId,
            @RequestBody TeamNameUpdateRequest request
    ) {
        log.info("[TEAM:PATCH] sport={}, league={}, teamId={}, newName={}", sport, league, teamId, request.getNewTeamName());
        return ResponseEntity.ok(teamInfoService.updateTeamName(teamId, request.getNewTeamName()));
    }

    /**
     * 새로운 팀 정보 추가
     * POST /api/match/{sport}/{league}/teams
     */
    @PostMapping("/teams")
    public ResponseEntity<ResponseData<TeamInfoDto>> createTeam(
            @PathVariable String sport,
            @PathVariable String league,
            @RequestBody TeamInfoDto teamDto
    ) {
        log.info("[TEAM:POST] sport={}, league={}, teamDto={}", sport, league, teamDto);
        return ResponseEntity.ok(teamInfoService.createTeam(teamDto));
    }

    /**
     * 두 팀 맞대결(H2H) 최근 경기
     * GET /api/match/{sport}/{league}/h2h?home={id}&away={id}
     */
    @GetMapping("/h2h")
    public List<MatchCardDto> getHeadToHead(
            @PathVariable String sport,
            @PathVariable String league,
            @RequestParam Long home,
            @RequestParam Long away
    ) {
        log.info("[H2H] sport={}, league={}, home={}, away={}", sport, league, home, away);
        return matchService.getHeadToHead(home, away);
    }

    // 요청 바디 모델
    public static class TeamNameUpdateRequest {
        private String newTeamName;
        public String getNewTeamName() { return newTeamName; }
        public void setNewTeamName(String newTeamName) { this.newTeamName = newTeamName; }
    }
    @GetMapping("/recent/home")
    public List<MatchCardDto> getHomeRecent(
            @PathVariable String sport,
            @PathVariable String league,
            @RequestParam(required = false) Long teamId
    ) {
        String mappedNickname = LeagueNameMapper.toDbNickname(league);

    // sport + nickname 으로 리그 PK(Long) 조회
    Long leagueId = leagueRepository.findIdBySportAndNickname(sport, mappedNickname)
            .orElseGet(() -> leagueRepository.findIdBySportAndNickname(sport, league)
                    .orElseThrow(() ->
                            new IllegalArgumentException("리그를 찾을 수 없습니다: " + sport + "/" + league)));
       
        return matchService.getRecentHomeMatch(leagueId, teamId );
    }
    @GetMapping("/recent/away")
    public List<MatchCardDto> getAwayRecent(
            @PathVariable String sport,
            @PathVariable String league,
            @RequestParam(required = false) Long teamId
    ) {
        String mappedNickname = LeagueNameMapper.toDbNickname(league);

    // sport + nickname 으로 리그 PK(Long) 조회
    Long leagueId = leagueRepository.findIdBySportAndNickname(sport, mappedNickname)
            .orElseGet(() -> leagueRepository.findIdBySportAndNickname(sport, league)
                    .orElseThrow(() ->
                            new IllegalArgumentException("리그를 찾을 수 없습니다: " + sport + "/" + league)));
       
        return matchService.getRecentAwayMatch(leagueId, teamId );
    }

    
}
