package kr.gg.compick.matchtag.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.gg.compick.domain.user.Matchtag;

@Repository
public interface MatchtagRepository extends JpaRepository<Matchtag, Long>{
    Optional<Matchtag> findByMatchtagName(String matchtagName);
}