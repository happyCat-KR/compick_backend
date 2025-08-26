package kr.gg.compick.match.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.gg.compick.domain.League;

import java.util.Optional;
public interface LeagueRepository extends JpaRepository<League, Long> {

     @Query("""
        SELECT l.leagueId FROM League l
        JOIN l.sport s
        WHERE LOWER(s.sportCode) = LOWER(:sport)
          AND LOWER(l.leagueNickname) = LOWER(:league)
    """)
    Optional<Long> findIdBySportAndNickname(@Param("sport") String sport,
                                            @Param("league") String league);

    // 추가: nickname OR code OR name 으로 유연 매칭
    @Query("""
        SELECT l.leagueId FROM League l
        JOIN l.sport s
        WHERE LOWER(s.sportCode) = LOWER(:sport)
          AND (
               LOWER(l.leagueNickname) = LOWER(:key)
            OR LOWER(l.leagueCode)     = LOWER(:key)
            OR LOWER(l.leagueName)     = LOWER(:key)
          )
    """)
    Optional<Long> findIdByAny(@Param("sport") String sport, @Param("key") String key);

    // (선택) 공백/하이픈 유연 비교가 필요하면 LIKE도 준비
    @Query("""
        SELECT l.leagueId FROM League l
        JOIN l.sport s
        WHERE LOWER(s.sportCode) = LOWER(:sport)
          AND REPLACE(LOWER(l.leagueName), '-', ' ') LIKE CONCAT('%', REPLACE(LOWER(:key), '-', ' '), '%')
    """)
    Optional<Long> findIdByNameLike(@Param("sport") String sport, @Param("key") String key);
}
