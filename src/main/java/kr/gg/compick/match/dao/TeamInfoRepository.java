package kr.gg.compick.match.dao;

import kr.gg.compick.domain.TeamInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamInfoRepository extends JpaRepository<TeamInfo, Long> {
    
    /**
     * 팀명으로 팀 정보 조회
     */
    Optional<TeamInfo> findByTeamName(String teamName);
    
    /**
     * 팀명에 특정 키워드가 포함된 팀들 조회
     */
    List<TeamInfo> findByTeamNameContainingIgnoreCase(String keyword);
    
    /**
     * 모든 팀 정보를 팀명 순으로 정렬하여 조회
     */
    @Query("SELECT t FROM TeamInfo t ORDER BY t.teamName")
    List<TeamInfo> findAllOrderByTeamName();
    
    /**
     * 특정 스포츠 리그의 팀들 조회 (리그 ID로 필터링)
     */
    @Query("SELECT t FROM TeamInfo t WHERE t.teamId IN " +
           "(SELECT DISTINCT m.homeTeam.teamId FROM Matches m WHERE m.league.leagueId = :leagueId " +
           "UNION " +
           "SELECT DISTINCT m.awayTeam.teamId FROM Matches m WHERE m.league.leagueId = :leagueId)")
    List<TeamInfo> findByLeagueId(@Param("leagueId") Long leagueId);
}

