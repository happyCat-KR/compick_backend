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
@RequestMapping("/api/{sport}/{league}/matches")
@RequiredArgsConstructor
public class MatchController {
    
    private final MatchService matchService;

// 범위 조회 (FullCalendar 기본: start/end)
@GetMapping(params = {"start","end"})
public List<MatchCardDto> range(
        @PathVariable String sport,
        @PathVariable String league,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
) {
    if (!end.isAfter(start)) {
        throw new IllegalArgumentException("end는 start보다 나중이어야 합니다.");
    }
    return matchService.getInRange(sport, league, start, end);
}

// 월 그리드 조회 (옵션)
@GetMapping(params = {"year","month"})
public List<MatchCardDto> monthlyGrid(
        @PathVariable String sport,
        @PathVariable String league,
        @RequestParam int year,
        @RequestParam int month
) 
{
    return matchService.getMonthlyGrid(sport, league, year, month);
}

// 상세 조회
@GetMapping("/{matchId}")
public MatchCardDto detail(
        @PathVariable String sport,
        @PathVariable String league,
        @PathVariable Long matchId
) {
    return matchService.getDetail(sport, league, matchId);
}
}
