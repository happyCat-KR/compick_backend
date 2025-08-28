package kr.gg.compick.match.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.gg.compick.domain.RankBaseball;

public interface BaseballRankRepository extends JpaRepository<RankBaseball, Long> {

    // 특정 리그 + 시즌 기준 순위 조회
    List<RankBaseball> findByLeague_LeagueIdAndSeasonOrderByRankAsc(Long leagueId, String season);

    // 시즌 전체 순위 조회
    List<RankBaseball> findBySeasonOrderByRankAsc(String season);
}
