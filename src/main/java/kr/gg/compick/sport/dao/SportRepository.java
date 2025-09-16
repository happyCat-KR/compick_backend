package kr.gg.compick.sport.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.gg.compick.domain.Sport;


@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {
    
   Optional<Sport> findBySportName(String sportName);
}
