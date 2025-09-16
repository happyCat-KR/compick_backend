package kr.gg.compick.match.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.gg.compick.domain.League;

import java.util.List;
import java.util.Optional;
public interface LeagueRepository extends JpaRepository<League, Long> {
Optional<League> findByLeagueName(String leagueName);
  @Query("""
  SELECT l.leagueId
  FROM League l
  JOIN l.sport s
  WHERE
    function('REPLACE', LOWER(TRIM(s.sportCode)), ' ', '') =
    function('REPLACE', LOWER(TRIM(:sport)),      ' ', '')
    AND (
      function('REPLACE', LOWER(TRIM(l.leagueNickname)), ' ', '') =
      function('REPLACE', LOWER(TRIM(:league)),         ' ', '')
      OR
      function('REPLACE', LOWER(TRIM(l.leagueNickname)), ' ', '') =
      function('REPLACE', LOWER(TRIM(:mappedNickname)),  ' ', '')
      OR
      function('REPLACE', LOWER(TRIM(l.leagueName)),     ' ', '') =
      function('REPLACE', LOWER(TRIM(:mappedNickname)),  ' ', '')
    )
""")
Optional<Long> findIdSmartIgnoringSpaces(@Param("sport") String sport,
                                         @Param("league") String league,
                                         @Param("mappedNickname") String mappedNickname);


  @Query("""
  SELECT l.leagueId
  FROM League l
  JOIN l.sport s
  WHERE
    function('REPLACE', LOWER(TRIM(s.sportCode)), ' ', '') =
    function('REPLACE', LOWER(TRIM(:sport)),      ' ', '')
    AND (
      function('REPLACE', LOWER(TRIM(l.leagueNickname)), ' ', '') =
      function('REPLACE', LOWER(TRIM(:mappedNickname)),  ' ', '')
      OR
      function('REPLACE', LOWER(TRIM(l.leagueName)),     ' ', '') =
      function('REPLACE', LOWER(TRIM(:mappedNickname)),  ' ', '')
    )
""")
Optional<Long> findbyLeagueId(@Param("sport") String sport,                                       
                              @Param("mappedNickname") String mappedNickname);

  
    @Query(value = """
        SELECT l.league_id
        FROM league l
        JOIN sport s ON l.sport_id = s.sport_id
        WHERE lower(s.sport_code) = lower(:sport)
          AND (
               lower(l.league_nickname) = lower(:league)
            OR lower(l.league_name)     = lower(:league)
          )
        LIMIT 1
    """, nativeQuery = true)
    Optional<Long> findIdBySportAndNickname(@Param("sport") String sport,
                                            @Param("league") String league);

    Optional<League> findByLeagueNickname(String leagueNickname);


  @Query("""
  SELECT CONCAT(
           CONCAT('nick=', COALESCE(l.leagueNickname, '')),
           CONCAT(', name=', COALESCE(l.leagueName, ''))
         )
    FROM League l JOIN l.sport s
    WHERE LOWER(TRIM(s.sportCode)) = LOWER(TRIM(:sport))
  """)
  List<String> dumpLeagueTriples(@Param("sport") String sport);


 

}