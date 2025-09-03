package kr.gg.compick.match.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.gg.compick.match.dao.BaseballRankRepository;
import kr.gg.compick.match.dao.SoccerRankRepository;
import kr.gg.compick.match.dto.BaseballRankDto;
import kr.gg.compick.match.dto.SoccerRankDto;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class RankService {

    private final BaseballRankRepository baseballRankRepository;
    private final SoccerRankRepository soccerRankRepository;

    public List<BaseballRankDto> getBaseballRanks(Long leagueId, String season) {
        return baseballRankRepository.findByLeague_LeagueIdAndSeasonOrderByRankNoAsc(leagueId, season)
                .stream()
                .map(BaseballRankDto::fromEntity)
                .toList();
    }

    public List<SoccerRankDto> getSoccerRanks(Long leagueId, String season) {
        return soccerRankRepository.findByLeague_LeagueIdAndSeasonOrderByRankAsc(leagueId, season)
                .stream()
                .map(SoccerRankDto::fromEntity)
                .toList();
    }
}