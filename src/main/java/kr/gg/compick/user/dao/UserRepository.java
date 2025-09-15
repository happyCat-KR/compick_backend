package kr.gg.compick.user.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.gg.compick.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
    boolean existsByUserNickname(String nickname);

    
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.userNickname = :nickname, u.introduction = :introduction, u.profileImage = :profileImg WHERE u.userId = :userId")
    int updateUserInfo(@Param("userId") String userId,
                       @Param("nickname") String nickname,
                       @Param("introduction") String introduction,
                       @Param("profileImg") String profileImg);
     Optional<User> findByUserId(String userId);

}
