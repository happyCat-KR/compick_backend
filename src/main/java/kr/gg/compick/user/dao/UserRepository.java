package kr.gg.compick.user.dao;


import kr.gg.compick.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByLoginId(String loginId);        // user_id
  Optional<User> findByEmail(String email);
  Optional<User> findByPhone(String phone);

  boolean existsByEmail(String email);
  boolean existsByLoginId(String loginId);
  boolean existsByPhone(String phone);

  // 논리삭제 제외 조회가 필요하면 이렇게도 사용 가능
  @Query("select u from User u where u.id=:id and u.deleted=false")
  Optional<User> findActiveById(Long id);
}
