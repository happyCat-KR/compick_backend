package kr.gg.compick.match.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.gg.compick.domain.RankSoccer;

public interface SoccerRankRepository extends JpaRepository<RankSoccer, Long> {

    // 특정 리그 + 시즌 기준 순위 조회
    List<RankSoccer> findByLeague_LeagueIdAndSeasonOrderByRankAsc(Long leagueId, String season);

    // 시즌 전체 순위 조회
    List<RankSoccer> findBySeasonOrderByRankAsc(String season);
}
