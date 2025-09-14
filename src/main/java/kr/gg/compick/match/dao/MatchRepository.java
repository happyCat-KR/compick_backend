package kr.gg.compick.match.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.gg.compick.domain.match.Matches;

@Repository
public interface MatchRepository extends JpaRepository<Matches, Long>{
    

  
}