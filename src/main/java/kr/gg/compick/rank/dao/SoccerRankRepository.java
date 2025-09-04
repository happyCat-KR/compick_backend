package kr.gg.compick.rank.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.gg.compick.domain.rank.RankSoccer;
import kr.gg.compick.domain.League;
import kr.gg.compick.domain.TeamInfo;

import java.util.List;
import java.util.Optional;

@Repository
public interface SoccerRankRepository extends JpaRepository<RankSoccer, Long> {

    /**
     * 스포츠와 리그로 축구 순위 조회
     */
    @Query("SELECT rs FROM RankSoccer rs " +
           "JOIN rs.league l " +
           "JOIN l.sport s " +
           "WHERE LOWER(s.sportCode) = LOWER(:sport) " +
           "AND LOWER(l.leagueNickname) = LOWER(:league) " +
           "AND rs.season = :season " +
           "ORDER BY rs.rank ASC")
    List<RankSoccer> findBySportAndLeagueAndSeason(
            @Param("sport") String sport,
            @Param("league") String league,
            @Param("season") String season
    );

    // 시즌 전체 순위 조회
    List<RankSoccer> findBySeasonOrderByRankAsc(String season);

    /**
     * 리그, 팀, 시즌으로 순위 조회
     */
    @Query("SELECT rs FROM RankSoccer rs " +
           "WHERE rs.league = :league " +
           "AND rs.teamInfo = :teamInfo " +
           "AND rs.season = :season")
    Optional<RankSoccer> findByLeagueAndTeamInfoAndSeason(
            @Param("league") League league,
            @Param("teamInfo") TeamInfo teamInfo,
            @Param("season") String season
    );

    /**
     * 리그와 시즌으로 순위 조회 (승점 내림차순, 득실차 내림차순)
     */
    @Query("SELECT rs FROM RankSoccer rs " +
           "WHERE rs.league = :league " +
           "AND rs.season = :season " +
           "ORDER BY rs.points DESC, rs.goalCount DESC")
    List<RankSoccer> findByLeagueAndSeasonOrderByPointsDescGoalCountDesc(
            @Param("league") League league,
            @Param("season") String season
    );
}
