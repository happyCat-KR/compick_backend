package kr.gg.compick.api;

import kr.gg.compick.match.dao.LeagueRepository;
import kr.gg.compick.match.dao.MatchCardProjection;
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
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final LeagueRepository leagueRepository;
    private final TeamInfoService teamInfoService;

    @PostConstruct
    public void init() {
        System.out.println("[MatchController] bean initialized: " + this.getClass().getName());
    }

    // GET /api/match/{sport}/{league}?start=...&end=...
    @GetMapping(value="/{sport}/{league}/", params = {"start", "end"})
    public List<MatchCardDto> range(
            @PathVariable("sport") String sport,
            @PathVariable("league") String league,
            @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(name = "end")   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        if (!end.isAfter(start)) throw new IllegalArgumentException("end는 start보다 나중이어야 합니다.");
        log.info("[RANGE] sport={}, league={}, start={}, end={}", sport, league, start, end);
        return matchService.getInRange(sport, league, start, end);
    }

    // GET /api/match/{sport}/{league}/monthly?year=&month=
    @GetMapping("/{sport}/{league}/monthly")
    public ResponseEntity<List<MatchCardDto>> monthly(
            @PathVariable("sport") String sport,
            @PathVariable("league") String league,
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month
    ) {
        log.info("[MONTHLY] sport={}, league={}, year={}, month={}", sport, league, year, month);
        List<MatchCardDto> result = matchService.getMonthlyGrid(sport, league, year, month);
        return ResponseEntity.ok()
                .header("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .body(result);
    }

    // GET /api/match/{sport}/{league}/{matchId}
    @GetMapping("/{sport}/{league}/{matchId}")
    public MatchCardDto detail(
            @PathVariable("sport") String sport,
            @PathVariable("league") String league,
            @PathVariable("matchId") Long matchId
    ) {
        log.info("[DETAIL] sport={}, league={}, matchId={}", sport, league, matchId);
        MatchCardDto dto = matchService.getDetail(sport, league, matchId);
        log.info("[DETAIL] homeTeamId={}, awayTeamId={}", dto.getHomeTeamId(), dto.getAwayTeamId());
        return dto;
    }

    // GET /api/match/{sport}/{league}/teams/{teamId}
    @GetMapping("/{sport}/{league}/teams/{teamId}")
    public ResponseEntity<ResponseData<TeamInfoDto>> getTeamById(
            @PathVariable("sport") String sport,
            @PathVariable("league") String league,
            @PathVariable("teamId") Long teamId
    ) {
        log.info("[TEAM:GET] sport={}, league={}, teamId={}", sport, league, teamId);
        return ResponseEntity.ok(teamInfoService.getTeamById(teamId));
    }

    // GET /api/match/search?keyword=...
   @GetMapping("/search")
        public ResponseEntity<List<MatchCardProjection>> searchMatches(
                @RequestParam String keyword
        ) {
        return ResponseEntity.ok(matchService.searchMatches(keyword));
        }

    // PATCH /api/match/{sport}/{league}/teams/{teamId}/name
    @PatchMapping("/{sport}/{league}/teams/{teamId}/name")
    public ResponseEntity<ResponseData<TeamInfoDto>> updateTeamName(
            @PathVariable("sport") String sport,
            @PathVariable("league") String league,
            @PathVariable("teamId") Long teamId,
            @RequestBody TeamNameUpdateRequest request
    ) {
        log.info("[TEAM:PATCH] sport={}, league={}, teamId={}, newName={}",
                sport, league, teamId, request.getNewTeamName());
        return ResponseEntity.ok(teamInfoService.updateTeamName(teamId, request.getNewTeamName()));
    }

    // POST /api/match/{sport}/{league}/teams
    @PostMapping("/{sport}/{league}/teams")
    public ResponseEntity<ResponseData<TeamInfoDto>> createTeam(
            @PathVariable("sport") String sport,
            @PathVariable("league") String league,
            @RequestBody TeamInfoDto teamDto
    ) {
        log.info("[TEAM:POST] sport={}, league={}, teamDto={}", sport, league, teamDto);
        return ResponseEntity.ok(teamInfoService.createTeam(teamDto));
    }

    // GET /api/match/{sport}/{league}/h2h?home=&away=
    @GetMapping("/{sport}/{league}/h2h")
    public List<MatchCardDto> getHeadToHead(
            @PathVariable("sport") String sport,
            @PathVariable("league") String league,
            @RequestParam(name = "home") Long home,
            @RequestParam(name = "away") Long away
    ) {
        log.info("[H2H] sport={}, league={}, home={}, away={}", sport, league, home, away);
        return matchService.getHeadToHead(home, away);
    }

    public static class TeamNameUpdateRequest {
        private String newTeamName;
        public String getNewTeamName() { return newTeamName; }
        public void setNewTeamName(String newTeamName) { this.newTeamName = newTeamName; }
    }

    // GET /api/match/{sport}/{league}/recent/home?teamId=
    @GetMapping("/{sport}/{league}/recent/home")
    public List<MatchCardDto> getHomeRecent(
            @PathVariable("sport") String sport,
            @PathVariable("league") String league,
            @RequestParam(name = "teamId", required = false) Long teamId
    ) {
        String mappedNickname = LeagueNameMapper.toDbNickname(league);
        Long leagueId = leagueRepository.findIdBySportAndNickname(sport, mappedNickname)
                .orElseGet(() -> leagueRepository.findIdBySportAndNickname(sport, league)
                        .orElseThrow(() -> new IllegalArgumentException("리그를 찾을 수 없습니다: " + sport + "/" + league)));
        return matchService.getRecentHomeMatch(leagueId, teamId);
    }

    // GET /api/match/{sport}/{league}/recent/away?teamId=
    @GetMapping("/{sport}/{league}/recent/away")
    public List<MatchCardDto> getAwayRecent(
            @PathVariable("sport") String sport,
            @PathVariable("league") String league,
            @RequestParam(name = "teamId", required = false) Long teamId
    ) {
        String mappedNickname = LeagueNameMapper.toDbNickname(league);
        Long leagueId = leagueRepository.findIdBySportAndNickname(sport, mappedNickname)
                .orElseGet(() -> leagueRepository.findIdBySportAndNickname(sport, league)
                        .orElseThrow(() -> new IllegalArgumentException("리그를 찾을 수 없습니다: " + sport + "/" + league)));
        return matchService.getRecentAwayMatch(leagueId, teamId);
    }
}
