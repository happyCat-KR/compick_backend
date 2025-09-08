package kr.gg.compick.match.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.gg.compick.domain.match.Matches;

@Repository
public interface MatchRepository extends JpaRepository<Matches, Long>{
    
    Optional<Matches> findById(Long id);
}
