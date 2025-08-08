package kr.gg.compick.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.gg.compick.domain.User;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByUserId(String userId);

    Optional<User> findByUserId(String userId);
}
