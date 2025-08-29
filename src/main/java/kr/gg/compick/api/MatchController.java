package kr.gg.compick.api;

import kr.gg.compick.match.dto.MatchCardDto;
import kr.gg.compick.match.dto.TeamInfoDto;
import kr.gg.compick.match.service.MatchService;
import kr.gg.compick.match.service.TeamInfoService;
import kr.gg.compick.util.ResponseData;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{sport}/{league}/matches")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MatchController {

    private final MatchService matchService;
    private final TeamInfoService teamInfoService;

    // 범위 조회 (FullCalendar 기본: start/end)
    @GetMapping(params = { "start", "end" })
    public List<MatchCardDto> range(
            @PathVariable String sport,
            @PathVariable String league,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("end는 start보다 나중이어야 합니다.");
        }
        return matchService.getInRange(sport, league, start, end);
    }

    // 월 그리드 조회 (옵션)
    @GetMapping(params = { "year", "month" })
    public List<MatchCardDto> monthlyGrid(
            @PathVariable("sport") String sport,
            @PathVariable("league") String league,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        return matchService.getMonthlyGrid(sport, league, year, month);
    }

    // 상세 조회
    @GetMapping("/{matchId}")
    public MatchCardDto detail(
            @PathVariable("sport") String sport,
            @PathVariable("league") String league,
            @PathVariable("matchId") Long matchId) {
        return matchService.getDetail(sport, league, matchId);
    }

    // ===== 팀 정보 관련 엔드포인트 =====

    /**
     * 모든 팀 정보 조회
     * GET /api/{sport}/{league}/matches/teams
     */
    @GetMapping("/teams")
    public ResponseEntity<ResponseData<List<TeamInfoDto>>> getAllTeams(
            @PathVariable String sport,
            @PathVariable String league) {
        return ResponseEntity.ok(teamInfoService.getAllTeams());
    }

    /**
     * 팀 ID로 팀 정보 조회
     * GET /api/{sport}/{league}/matches/teams/{teamId}
     */
    @GetMapping("/teams/{teamId}")
    public ResponseEntity<ResponseData<TeamInfoDto>> getTeamById(
            @PathVariable String sport,
            @PathVariable String league,
            @PathVariable Long teamId) {
        return ResponseEntity.ok(teamInfoService.getTeamById(teamId));
    }

    /**
     * 팀명으로 팀 검색
     * GET /api/{sport}/{league}/matches/teams/search?keyword={keyword}
     */
    @GetMapping("/teams/search")
    public ResponseEntity<ResponseData<List<TeamInfoDto>>> searchTeamsByName(
            @PathVariable String sport,
            @PathVariable String league,
            @RequestParam String keyword) {
        return ResponseEntity.ok(teamInfoService.searchTeamsByName(keyword));
    }

    /**
     * 팀명 업데이트 (한국어 팀명으로 패치)
     * PATCH /api/{sport}/{league}/matches/teams/{teamId}/name
     */
    @PatchMapping("/teams/{teamId}/name")
    public ResponseEntity<ResponseData<TeamInfoDto>> updateTeamName(
            @PathVariable String sport,
            @PathVariable String league,
            @PathVariable Long teamId,
            @RequestBody TeamNameUpdateRequest request) {
        return ResponseEntity.ok(teamInfoService.updateTeamName(teamId, request.getNewTeamName()));
    }

    /**
     * 새로운 팀 정보 추가
     * POST /api/{sport}/{league}/matches/teams
     */
    @PostMapping("/teams")
    public ResponseEntity<ResponseData<TeamInfoDto>> createTeam(
            @PathVariable String sport,
            @PathVariable String league,
            @RequestBody TeamInfoDto teamDto) {
        return ResponseEntity.ok(teamInfoService.createTeam(teamDto));
    }

    /**
     * 팀명 업데이트 요청을 위한 내부 클래스
     */
    public static class TeamNameUpdateRequest {
        private String newTeamName;

        public String getNewTeamName() {
            return newTeamName;
        }

        public void setNewTeamName(String newTeamName) {
            this.newTeamName = newTeamName;
        }
    }
}
