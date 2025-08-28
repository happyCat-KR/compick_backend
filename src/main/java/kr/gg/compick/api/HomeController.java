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
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {
    
    private final MatchService matchService;

    /**
     * 홈 화면 - 모든 스포츠와 모든 리그의 경기들을 나열
     * 기본적으로 이번 주(월~일)의 경기를 조회
     * 선택적 파라미터: sport, league, year, month
     */
    @GetMapping("/matches")
    public List<MatchCardDto> getAllMatches(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(required = false) String sport,
            @RequestParam(required = false) String league,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month
    ) {
        // sport와 league가 모두 제공된 경우 조건부 조회
        if (sport != null && league != null) {
            // year와 month가 제공된 경우 월별 그리드 조회
            if (year != null && month != null) {
                return matchService.getMonthlyGrid(sport, league, year, month);
            }
            
            // start와 end가 제공된 경우 범위 조회
            if (start != null && end != null) {
                return matchService.getInRange(sport, league, start, end);
            }
            
            // 기본값 설정: 이번 주(월~일)
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime defaultStart = now.with(java.time.DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime defaultEnd = defaultStart.plusWeeks(1);
            
            return matchService.getInRange(sport, league, defaultStart, defaultEnd);
        }
        
        // sport만 제공된 경우
        if (sport != null) {
            // sport가 "all"인 경우 모든 스포츠/리그 조회
            if ("all".equalsIgnoreCase(sport)) {
                // year와 month가 제공된 경우 월별 그리드 조회
                if (year != null && month != null) {
                    return matchService.getAllMatchesMonthlyGrid(year, month);
                }
                
                // start와 end가 제공된 경우 범위 조회
                if (start != null && end != null) {
                    return matchService.getAllMatchesInRange(start, end);
                }
                
                // 기본값 설정: 이번 주(월~일)
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime defaultStart = now.with(java.time.DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0);
                LocalDateTime defaultEnd = defaultStart.plusWeeks(1);
                
                return matchService.getAllMatchesInRange(defaultStart, defaultEnd);
            }
            
            // 특정 스포츠인 경우 해당 스포츠의 모든 리그 조회
            // year와 month가 제공된 경우 월별 그리드 조회
            if (year != null && month != null) {
                return matchService.getMonthlyGrid(sport, "all", year, month);
            }
            
            // start와 end가 제공된 경우 범위 조회
            if (start != null && end != null) {
                return matchService.getInRange(sport, "all", start, end);
            }
            
            // 기본값 설정: 이번 주(월~일)
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime defaultStart = now.with(java.time.DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime defaultEnd = defaultStart.plusWeeks(1);
            
            return matchService.getInRange(sport, "all", defaultStart, defaultEnd);
        }
        
        // 모든 파라미터가 없는 경우 기존 로직 (모든 스포츠/리그)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime defaultStart = start != null ? start : now.with(java.time.DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime defaultEnd = end != null ? end : defaultStart.plusWeeks(1);
        
        return matchService.getAllMatchesInRange(defaultStart, defaultEnd);
    }

    /**
     * 홈 화면 - 오늘 경기만 조회
     * 선택적 파라미터: sport, league
     */
    @GetMapping("/matches/today")
    public List<MatchCardDto> getTodayMatches(
            @RequestParam(required = false) String sport,
            @RequestParam(required = false) String league
    ) {
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime tomorrow = today.plusDays(1);
        
        // sport와 league가 모두 제공된 경우
        if (sport != null && league != null) {
            return matchService.getInRange(sport, league, today, tomorrow);
        }
        
        // sport만 제공된 경우
        if (sport != null) {
            // sport가 "all"인 경우 모든 스포츠/리그 조회
            if ("all".equalsIgnoreCase(sport)) {
                return matchService.getAllMatchesInRange(today, tomorrow);
            }
            
            // 특정 스포츠인 경우 해당 스포츠의 모든 리그 조회
            return matchService.getInRange(sport, "all", today, tomorrow);
        }
        
        // 모든 파라미터가 없는 경우 기존 로직 (모든 스포츠/리그)
        return matchService.getAllMatchesInRange(today, tomorrow);
    }

    /**
     * 홈 화면 - 이번 주 경기 조회
     * 선택적 파라미터: sport, league
     */
    @GetMapping("/matches/thisweek")
    public List<MatchCardDto> getThisWeekMatches(
            @RequestParam(required = false) String sport,
            @RequestParam(required = false) String league
    ) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekStart = now.with(java.time.DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime weekEnd = weekStart.plusWeeks(1);
        
        // sport와 league가 모두 제공된 경우
        if (sport != null && league != null) {
            return matchService.getInRange(sport, league, weekStart, weekEnd);
        }
        
        // sport만 제공된 경우
        if (sport != null) {
            // sport가 "all"인 경우 모든 스포츠/리그 조회
            if ("all".equalsIgnoreCase(sport)) {
                return matchService.getAllMatchesInRange(weekStart, weekEnd);
            }
            
            // 특정 스포츠인 경우 해당 스포츠의 모든 리그 조회
            return matchService.getInRange(sport, "all", weekStart, weekEnd);
        }
        
        // 모든 파라미터가 없는 경우 기존 로직 (모든 스포츠/리그)
        return matchService.getAllMatchesInRange(weekStart, weekEnd);
    }

    /**
     * 홈 화면 - 캘린더 월별 조회 (FullCalendar 월 그리드 대응)
     * 해당 월을 월~일로 꽉 채운 범위의 경기를 조회
     * 선택적 파라미터: sport, league
     * 프론트엔드 호환성을 위해 getAllMatchesMonthly 함수와 동일한 로직 적용
     */
    @GetMapping("/matches/monthly")
    public List<MatchCardDto> getMonthlyMatches(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam(required = false) String sport,
            @RequestParam(required = false) String league
    ) {
        // sport와 league가 모두 제공된 경우
        if (sport != null && league != null) {
            return matchService.getMonthlyGrid(sport, league, year, month);
        }
        
        // sport만 제공된 경우
        if (sport != null) {
            // sport가 "all"인 경우 모든 스포츠/리그 조회
            if ("all".equalsIgnoreCase(sport)) {
                return matchService.getAllMatchesMonthlyGrid(year, month);
            }
            
            // 특정 스포츠인 경우 해당 스포츠의 모든 리그 조회
            return matchService.getMonthlyGrid(sport, "all", year, month);
        }
        
        // 모든 파라미터가 없는 경우 기존 로직 (모든 스포츠/리그)
        return matchService.getAllMatchesMonthlyGrid(year, month);
    }

    /**
     * 프론트엔드 호환성을 위한 월별 조회 엔드포인트 (별칭)
     * getAllMatchesMonthly 함수와 호환
     */
    @GetMapping("/matches/monthly/all")
    public List<MatchCardDto> getAllMatchesMonthly(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam(required = false) String sport,
            @RequestParam(required = false) String league
    ) {
        // 기존 getMonthlyMatches와 동일한 로직 사용
        return getMonthlyMatches(year, month, sport, league);
    }

    /**
     * 홈 화면 - 특정 날짜의 경기 조회
     * 선택적 파라미터: sport, league
     */
    @GetMapping("/matches/date")
    public List<MatchCardDto> getDateMatches(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.time.LocalDate date,
            @RequestParam(required = false) String sport,
            @RequestParam(required = false) String league
    ) {
        LocalDateTime dayStart = date.atStartOfDay();
        LocalDateTime dayEnd = dayStart.plusDays(1);
        
        // sport와 league가 모두 제공된 경우
        if (sport != null && league != null) {
            return matchService.getInRange(sport, league, dayStart, dayEnd);
        }
        
        // sport만 제공된 경우
        if (sport != null) {
            // sport가 "all"인 경우 모든 스포츠/리그 조회
            if ("all".equalsIgnoreCase(sport)) {
                return matchService.getAllMatchesInRange(dayStart, dayEnd);
            }
            
            // 특정 스포츠인 경우 해당 스포츠의 모든 리그 조회
            return matchService.getInRange(sport, "all", dayStart, dayEnd);
        }
        
        // 모든 파라미터가 없는 경우 기존 로직 (모든 스포츠/리그)
        return matchService.getAllMatchesInRange(dayStart, dayEnd);
    }

    /**
     * 홈 화면 - 특정 날짜의 경기 조회 (날짜 형식: yyyy-MM-dd)
     * 선택적 파라미터: sport, league
     */
    @GetMapping("/matches/date/{year}/{month}/{day}")
    public List<MatchCardDto> getDateMatchesByPath(
            @PathVariable int year,
            @PathVariable int month,
            @PathVariable int day,
            @RequestParam(required = false) String sport,
            @RequestParam(required = false) String league
    ) {
        java.time.LocalDate date = java.time.LocalDate.of(year, month, day);
        LocalDateTime dayStart = date.atStartOfDay();
        LocalDateTime dayEnd = dayStart.plusDays(1);
        
        // sport와 league가 모두 제공된 경우
        if (sport != null && league != null) {
            return matchService.getInRange(sport, league, dayStart, dayEnd);
        }
        
        // sport만 제공된 경우
        if (sport != null) {
            // sport가 "all"인 경우 모든 스포츠/리그 조회
            if ("all".equalsIgnoreCase(sport)) {
                return matchService.getAllMatchesInRange(dayStart, dayEnd);
            }
            
            // 특정 스포츠인 경우 해당 스포츠의 모든 리그 조회
            return matchService.getInRange(sport, "all", dayStart, dayEnd);
        }
        
        // 모든 파라미터가 없는 경우 기존 로직 (모든 스포츠/리그)
        return matchService.getAllMatchesInRange(dayStart, dayEnd);
    }


}
