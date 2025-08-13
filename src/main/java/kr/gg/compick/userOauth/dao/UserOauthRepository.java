package kr.gg.compick.userOauth.dao;


import kr.gg.compick.domain.UserOauth;
import kr.gg.compick.domain.UserOauthId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOauthRepository extends JpaRepository<UserOauth, UserOauthId> {
  // provider + provider_user_id 로 조회 (소셜 재로그인)
  Optional<UserOauth> findByIdProviderAndIdProviderUserId(String provider, String providerUserId);

  // 특정 유저의 특정 provider 연동(중복 방지 유니크와 함께 사용)
  Optional<UserOauth> findByUser_IdAndIdProvider(Long userId, String provider);

  // 유저의 모든 소셜 계정
  List<UserOauth> findAllByUser_Id(Long userId);

  boolean existsByIdProviderAndIdProviderUserId(String provider, String providerUserId);
}
