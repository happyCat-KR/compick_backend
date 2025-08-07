package kr.gg.compick.match.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fasterxml.jackson.databind.deser.DataFormatReaders.Match;

public interface MatchRepository extends JpaRepository<Match, Integer> {

}
