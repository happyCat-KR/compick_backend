package kr.gg.compick.rank.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.gg.compick.domain.rank.RankBaseball;

import java.util.List;

@Repository
public interface BaseballRankRepository extends JpaRepository<RankBaseball, Long> {
    
    /**
     * 리그와 시즌으로 야구 순위 조회
     */
    @Query("SELECT rb FROM RankBaseball rb " +
           "JOIN rb.league l " +
           "WHERE l.leagueId = :leagueId AND rb.season = :season " +
           "ORDER BY rb.rank ASC")
    List<RankBaseball> findByLeague_LeagueIdAndSeasonOrderByRankAsc(
            @Param("leagueId") Long leagueId, 
            @Param("season") String season
    );
    
    /**
     * 스포츠와 리그로 야구 순위 조회
     */
    @Query("SELECT rb FROM RankBaseball rb " +
           "JOIN rb.league l " +
           "JOIN l.sport s " +
           "WHERE LOWER(s.sportCode) = LOWER(:sport) " +
           "AND LOWER(l.leagueNickname) = LOWER(:league) " +
           "AND rb.season = :season " +
           "ORDER BY rb.rank ASC")
    List<RankBaseball> findBySportAndLeagueAndSeason(
            @Param("sport") String sport,
            @Param("league") String league,
            @Param("season") String season
    );
}
