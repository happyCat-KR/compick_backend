package kr.gg.compick.refreshToken.dao;

import kr.gg.compick.domain.RefreshToken;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByJti(String jti);
  List<RefreshToken> findAllByUser_IdAndRevokedFalse(Long userId);

  // 사용자 전 기기 로그아웃
  @Modifying @Transactional
  @Query("update RefreshToken r set r.revoked=true where r.user.id=:userId and r.revoked=false")
  int revokeAllForUser(Long userId);

  // 오래된 토큰 정리
  @Modifying @Transactional
  @Query("delete from RefreshToken r where r.expiresAt < :now")
  int purgeExpired(LocalDateTime now);
}
