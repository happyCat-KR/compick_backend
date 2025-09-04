package kr.gg.compick.api;
// package kr.gg.compick.api.rank;

// import java.util.List;

// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import kr.gg.compick.rank.dto.BaseballRankDto;
// import kr.gg.compick.rank.dto.SoccerRankDto;
// import kr.gg.compick.rank.service.RankService;
// import lombok.RequiredArgsConstructor;


// @RestController
// @RequestMapping("/api/{sport}/{league}/rank")
// @RequiredArgsConstructor
// @CrossOrigin(origins = "*")
// public class RankController {

//     private final RankService rankService;

//     /**
//      * ⚾ 야구 순위 조회
//      * ex) GET /api/rank/baseball/1?season=2025
//      */
//     @GetMapping(params = {"sport","league","season"})
//     public List<BaseballRankDto> getBaseballRank(
//             @PathVariable String sport,
//             @PathVariable String league,
//             @RequestParam String season
//     ) {
//         return rankService.getBaseballRanks(sport, league, season);
//     }

//     /**
//      * ⚽ 축구 순위 조회
//      * ex) GET /api/rank/soccer/2?season=2025-26
//      */
//     @GetMapping(params = {"sport","league","season"})
//     public List<SoccerRankDto> getSoccerRank(
//         @PathVariable String sport,
//         @PathVariable String league,
//         @RequestParam String season
//     ) {
//         return rankService.getSoccerRanks(sport, league, season);
//     }
// }