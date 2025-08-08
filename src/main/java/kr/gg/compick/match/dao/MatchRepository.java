package kr.gg.compick.match.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.gg.compick.domain.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {

}
