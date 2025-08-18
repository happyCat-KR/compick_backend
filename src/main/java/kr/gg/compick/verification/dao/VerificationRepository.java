package kr.gg.compick.verification.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.gg.compick.domain.VerificationCode;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationCode, Long> {

    List<VerificationCode> findAllByDestinationAndPurposeAndConsumedAtIsNull(String destination, String purpose);

    Optional<VerificationCode> findTopByDestinationAndPurposeOrderByCreatedAtDesc(
            String destination,
            String purpose
    );
}
