package kr.gg.compick.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.gg.compick.match.dto.MatchCardDto;
import kr.gg.compick.match.service.MatchService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/{sport}/{league}/match/monthly")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MatchMonthlyController {
    
    private final MatchService matchService;

    /**
     * 월별 경기 조회
     * GET /api/{sport}/{league}/match/monthly?year=2025&month=8
     */
    @GetMapping
    public List<MatchCardDto> monthly(
            @PathVariable String sport,
            @PathVariable String league,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return matchService.getMonthlyGrid(sport, league, year, month);
    }
}
