package kr.gg.compick.userOauth.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.gg.compick.domain.UserOauth;
import kr.gg.compick.domain.UserOauthId;

public interface UserOauthRepository extends JpaRepository<UserOauth, UserOauthId>{
    boolean existsById(UserOauthId id);
}
