package kr.gg.compick.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.gg.compick.match.dto.BaseballRankDto;
import kr.gg.compick.match.dto.SoccerRankDto;
import kr.gg.compick.match.service.RankService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/rank")
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;

    /**
     * ⚾ 야구 순위 조회
     * ex) GET /api/rank/baseball/1?season=2025
     */
    @GetMapping("/baseball/{leagueId}")
    public List<BaseballRankDto> getBaseballRank(
            @PathVariable Long leagueId,
            @RequestParam String season
    ) {
        return rankService.getBaseballRanks(leagueId, season);
    }

    /**
     * ⚽ 축구 순위 조회
     * ex) GET /api/rank/soccer/2?season=2025-26
     */
    @GetMapping("/soccer/{leagueId}")
    public List<SoccerRankDto> getSoccerRank(
            @PathVariable Long leagueId,
            @RequestParam String season
    ) {
        return rankService.getSoccerRanks(leagueId, season);
    }
}