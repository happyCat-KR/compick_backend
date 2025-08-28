package kr.gg.compick.match.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.gg.compick.domain.Matches;

@Repository
public interface MatchCalenderRepository extends JpaRepository<Matches, Long> {

    /**
     * 리그 단건 범위 조회
     * - sport / leagueNickname 도 함께 반환 (프론트 null 방지)
     */
    @Query(value = """
        SELECT
          ms.match_id                     AS matchId,
          sp.sport_name                   AS sport,
          l.league_nickname               AS leagueNickname,
          l.league_name                   AS leagueName,
          l.image_url                     AS leagueLogo,
          th.team_name                    AS homeTeamName,
          th.image_url                    AS homeTeamLogo,
          ta.team_name                    AS awayTeamName,
          ta.image_url                    AS awayTeamLogo,
          COALESCE(sh.score, 0)           AS homeScore,
          COALESCE(sa.score, 0)           AS awayScore,
          ms.start_time                   AS startTime,
          mc.description                  AS matchStatus
        FROM (
            SELECT *
            FROM matches
            WHERE league_id = :leagueId
              AND start_time >= :start
              AND start_time <  :end
        ) ms
        JOIN team_info     th ON ms.home_team_id = th.team_id
        JOIN team_info     ta ON ms.away_team_id = ta.team_id
        JOIN league         l ON ms.league_id    = l.league_id
        JOIN sport         sp ON l.sport_id      = sp.sport_id
        JOIN match_status  mc ON ms.status_code  = mc.code
        LEFT JOIN match_score sh ON sh.match_id = ms.match_id AND sh.team_id = ms.home_team_id
        LEFT JOIN match_score sa ON sa.match_id = ms.match_id AND sa.team_id = ms.away_team_id
        ORDER BY ms.start_time
        """, nativeQuery = true)
    List<MatchCardProjection> findInRange(
        @Param("leagueId") Long leagueId,
        @Param("start") LocalDateTime start,
        @Param("end")   LocalDateTime end
    );

    /**
     * 스포츠 전체 범위 조회 (league=all 대응)
     * - sport_code 로 필터, 같은 Projection 반환
     */
    @Query(value = """
        SELECT
          ms.match_id                     AS matchId,
          sp.sport_name                   AS sport,
          l.league_nickname               AS leagueNickname,
          l.league_name                   AS leagueName,
          l.image_url                     AS leagueLogo,
          th.team_name                    AS homeTeamName,
          th.image_url                    AS homeTeamLogo,
          ta.team_name                    AS awayTeamName,
          ta.image_url                    AS awayTeamLogo,
          COALESCE(sh.score, 0)           AS homeScore,
          COALESCE(sa.score, 0)           AS awayScore,
          ms.start_time                   AS startTime,
          mc.description                  AS matchStatus
        FROM matches ms
        JOIN league         l  ON ms.league_id    = l.league_id
        JOIN sport          sp ON l.sport_id      = sp.sport_id
        JOIN team_info      th ON ms.home_team_id = th.team_id
        JOIN team_info      ta ON ms.away_team_id = ta.team_id
        JOIN match_status   mc ON ms.status_code  = mc.code
        LEFT JOIN match_score sh ON sh.match_id = ms.match_id AND sh.team_id = ms.home_team_id
        LEFT JOIN match_score sa ON sa.match_id = ms.match_id AND sa.team_id = ms.away_team_id
        WHERE LOWER(sp.sport_code) = LOWER(:sport)
          AND ms.start_time >= :start
          AND ms.start_time <  :end
        ORDER BY ms.start_time
        """, nativeQuery = true)
    List<MatchCardProjection> findInRangeBySport(
        @Param("sport") String sport,
        @Param("start") LocalDateTime start,
        @Param("end")   LocalDateTime end
    );

    /**
     * 상세조회 (기존 그대로)
     */
    @Query(value = """
        SELECT
          ms.match_id            AS matchId,
          sp.sport_name          AS sport,
          l.league_nickname      AS leagueNickname,
          l.league_name          AS leagueName,
          l.image_url            AS leagueLogo,
          th.team_name           AS homeTeamName,
          th.image_url           AS homeTeamLogo,
          ta.team_name           AS awayTeamName,
          ta.image_url           AS awayTeamLogo,
          COALESCE(sh.score, 0)  AS homeScore,
          COALESCE(sa.score, 0)  AS awayScore,
          ms.start_time          AS startTime,
          mc.description         AS matchStatus
        FROM matches ms
        JOIN team_info th        ON ms.home_team_id = th.team_id
        JOIN team_info ta        ON ms.away_team_id = ta.team_id
        JOIN league l            ON ms.league_id    = l.league_id
        JOIN match_status mc     ON ms.status_code  = mc.code
        JOIN sport sp            ON l.sport_id      = sp.sport_id
        LEFT JOIN match_score sh ON sh.match_id = ms.match_id AND sh.team_id = ms.home_team_id
        LEFT JOIN match_score sa ON sa.match_id = ms.match_id AND sa.team_id = ms.away_team_id
        WHERE ms.league_id = :leagueId
          AND ms.match_id  = :matchId
        """, nativeQuery = true)
    MatchCardProjection findOneCard(
        @Param("leagueId") Long leagueId,
        @Param("matchId")  Long matchId
    );

    // 필요시: 엔티티 기반 파생 쿼리 유지
    List<Matches> findByLeague_LeagueIdAndStartTimeGreaterThanEqualAndStartTimeLessThan(
        Long leagueId,
        LocalDateTime start,
        LocalDateTime end
    );

    /**
     * 홈 화면용: 모든 스포츠와 모든 리그의 경기를 조회
     * - sport / leagueNickname 도 함께 반환 (프론트 null 방지)
     */
    @Query(value = """
        SELECT
          ms.match_id                     AS matchId,
          sp.sport_name                   AS sport,
          l.league_nickname               AS leagueNickname,
          l.league_name                   AS leagueName,
          l.image_url                     AS leagueLogo,
          th.team_name                    AS homeTeamName,
          th.image_url                    AS homeTeamLogo,
          ta.team_name                    AS awayTeamName,
          ta.image_url                    AS awayTeamLogo,
          COALESCE(sh.score, 0)           AS homeScore,
          COALESCE(sa.score, 0)           AS awayScore,
          ms.start_time                   AS startTime,
          mc.description                  AS matchStatus
        FROM matches ms
        JOIN team_info     th ON ms.home_team_id = th.team_id
        JOIN team_info     ta ON ms.away_team_id = ta.team_id
        JOIN league         l ON ms.league_id    = l.league_id
        JOIN sport         sp ON l.sport_id      = sp.sport_id
        JOIN match_status  mc ON ms.status_code  = mc.code
        LEFT JOIN match_score sh ON sh.match_id = ms.match_id AND sh.team_id = ms.home_team_id
        LEFT JOIN match_score sa ON sa.match_id = ms.match_id AND sa.team_id = ms.away_team_id
        WHERE ms.start_time >= :start
          AND ms.start_time <  :end
        ORDER BY ms.start_time, sp.sport_name, l.league_nickname
        """, nativeQuery = true)
    List<MatchCardProjection> findAllMatchesInRange(
        @Param("start") LocalDateTime start,
        @Param("end")   LocalDateTime end
    );

    /**
     * 경기 ID로 상세 조회 (스포츠/리그 제한 없음)
     */
    @Query(value = """
        SELECT
          ms.match_id            AS matchId,
          sp.sport_name          AS sport,
          l.league_nickname      AS leagueNickname,
          l.league_name          AS leagueName,
          l.image_url            AS leagueLogo,
          th.team_name           AS homeTeamName,
          th.image_url           AS homeTeamLogo,
          ta.team_name           AS awayTeamName,
          ta.image_url           AS awayTeamLogo,
          COALESCE(sh.score, 0)  AS homeScore,
          COALESCE(sa.score, 0)  AS awayScore,
          ms.start_time          AS startTime,
          mc.description         AS matchStatus
        FROM matches ms
        JOIN team_info th        ON ms.home_team_id = th.team_id
        JOIN team_info ta        ON ms.away_team_id = ta.team_id
        JOIN league l            ON ms.league_id    = l.league_id
        JOIN match_status mc     ON ms.status_code  = mc.code
        JOIN sport sp            ON l.sport_id      = sp.sport_id
        LEFT JOIN match_score sh ON sh.match_id = ms.match_id AND sh.team_id = ms.home_team_id
        LEFT JOIN match_score sa ON sa.match_id = ms.match_id AND sa.team_id = ms.away_team_id
        WHERE ms.match_id = :matchId
        """, nativeQuery = true)
    MatchCardProjection findOneCardByMatchId(@Param("matchId") Long matchId);
}