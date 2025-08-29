package kr.gg.compick.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.gg.compick.match.dto.MatchCardDto;
import kr.gg.compick.match.service.MatchService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/all")
@RequiredArgsConstructor
public class AllMatchesController {

    private final MatchService matchService;

    /**
     * 모든 스포츠의 모든 리그 경기 조회
     * 기본적으로 이번 주(월~일)의 경기를 조회
     */
    @GetMapping("/matches")
    public List<MatchCardDto> getAllSportsMatches(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        // 기본값 설정: 이번 주(월~일)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime defaultStart = start != null ? start
                : now.with(java.time.DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime defaultEnd = end != null ? end : defaultStart.plusWeeks(1);

        return matchService.getAllMatchesInRange(defaultStart, defaultEnd);
    }

    /**
     * 모든 스포츠의 모든 리그 - 오늘 경기만 조회
     */
    @GetMapping("/matches/today")
    public List<MatchCardDto> getAllSportsTodayMatches() {
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime tomorrow = today.plusDays(1);

        return matchService.getAllMatchesInRange(today, tomorrow);
    }

    /**
     * 모든 스포츠의 모든 리그 - 이번 주 경기 조회
     */
    @GetMapping("/matches/this-week")
    public List<MatchCardDto> getAllSportsThisWeekMatches() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekStart = now.with(java.time.DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime weekEnd = weekStart.plusWeeks(1);

        return matchService.getAllMatchesInRange(weekStart, weekEnd);
    }

    /**
     * 특정 스포츠의 모든 리그 경기 조회
     */
    @GetMapping("/{sport}/matches")
    public List<MatchCardDto> getSportAllLeaguesMatches(
            @PathVariable String sport,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        // 기본값 설정: 오늘부터 7일간
        LocalDateTime defaultStart = start != null ? start
                : LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime defaultEnd = end != null ? end : defaultStart.plusDays(7);

        return matchService.getInRange(sport, "all", defaultStart, defaultEnd);
    }

    /**
     * 특정 스포츠의 모든 리그 - 오늘 경기만 조회
     */
    @GetMapping("/{sport}/matches/today")
    public List<MatchCardDto> getSportAllLeaguesTodayMatches(@PathVariable String sport) {
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime tomorrow = today.plusDays(1);

        return matchService.getInRange(sport, "all", today, tomorrow);
    }

    /**
     * 특정 스포츠의 모든 리그 - 이번 주 경기 조회
     */
    @GetMapping("/{sport}/matches/this-week")
    public List<MatchCardDto> getSportAllLeaguesThisWeekMatches(@PathVariable String sport) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekStart = now.with(java.time.DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime weekEnd = weekStart.plusWeeks(1);

        return matchService.getInRange(sport, "all", weekStart, weekEnd);
    }

    /**
     * 모든 스포츠의 모든 리그 - 캘린더 월별 조회
     */
    @GetMapping("/matches/monthly")
    public List<MatchCardDto> getAllSportsMonthlyMatches(
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        return matchService.getAllMatchesMonthlyGrid(year, month);
    }

    /**
     * 모든 스포츠의 모든 리그 - 특정 날짜의 경기 조회
     */
    @GetMapping("/matches/date")
    public List<MatchCardDto> getAllSportsDateMatches(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.time.LocalDate date) {
        LocalDateTime dayStart = date.atStartOfDay();
        LocalDateTime dayEnd = dayStart.plusDays(1);

        return matchService.getAllMatchesInRange(dayStart, dayEnd);
    }

    /**
     * 모든 스포츠의 모든 리그 - 특정 날짜의 경기 조회 (경로 변수)
     */
    @GetMapping("/matches/date/{year}/{month}/{day}")
    public List<MatchCardDto> getAllSportsDateMatchesByPath(
            @PathVariable("year") int year,
            @PathVariable("month") int month,
            @PathVariable("day") int day) {
        java.time.LocalDate date = java.time.LocalDate.of(year, month, day);
        LocalDateTime dayStart = date.atStartOfDay();
        LocalDateTime dayEnd = dayStart.plusDays(1);

        return matchService.getAllMatchesInRange(dayStart, dayEnd);
    }

    /**
     * 특정 스포츠의 모든 리그 - 캘린더 월별 조회
     */
    @GetMapping("/{sport}/matches/monthly")
    public List<MatchCardDto> getSportAllLeaguesMonthlyMatches(
            @PathVariable("sport") String sport,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        return matchService.getMonthlyGrid(sport, "all", year, month);
    }

    /**
     * 특정 스포츠의 모든 리그 - 특정 날짜의 경기 조회
     */
    @GetMapping("/{sport}/matches/date")
    public List<MatchCardDto> getSportAllLeaguesDateMatches(
            @PathVariable String sport,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.time.LocalDate date) {
        LocalDateTime dayStart = date.atStartOfDay();
        LocalDateTime dayEnd = dayStart.plusDays(1);

        return matchService.getInRange(sport, "all", dayStart, dayEnd);
    }
}
