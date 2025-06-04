package com.ambrosezen.weblearnenglish.repository;

import com.ambrosezen.weblearnenglish.entities.VerificationOtp;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationOtpRepository extends JpaRepository<VerificationOtp, UUID> {

  VerificationOtp findByOtp(String otp);
  //VerificationOtp findByUsersId(UUID userId);

}
