package kr.gg.compick.verificationCode.dao;

import kr.gg.compick.domain.VerificationCode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

  // 최신 미사용/미만료 코드 1건 (중복 발송 방지/최근 코드 검증)
  Optional<VerificationCode> findFirstByDestinationAndPurposeAndConsumedAtIsNullAndExpiresAtAfterOrderByCreatedAtDesc(
      String destination, String purpose, LocalDateTime now);

  // 만료/소모된 코드 정리
  @Modifying @Transactional
  @Query("delete from VerificationCode v where v.expiresAt < :now or v.consumedAt is not null")
  int purgeExpired(LocalDateTime now);
}
