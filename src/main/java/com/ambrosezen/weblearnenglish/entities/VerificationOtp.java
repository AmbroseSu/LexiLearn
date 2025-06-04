package com.ambrosezen.weblearnenglish.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import lombok.Data;
import org.springframework.security.core.userdetails.User;

@Data
@Entity
@Table(name = "tbl_verification_otp")
public class VerificationOtp {
  private static final int EXPRATION_TIME = 1;
  @Id
  @Column(name = "verification_otp_id", updatable = false, nullable = false)
  private UUID verificationOtpId;
  @PrePersist
  public void generateId() {
    if (verificationOtpId == null) {
      verificationOtpId = UUID.randomUUID();
    }
  }
  private String otp;
  private Date expirationTime;
  private boolean isTrue;
  private boolean isDeleted;
  private int attemptCount;

  @OneToOne
  @JoinColumn(name = "user_id")
  private Users users;

  public VerificationOtp(String otp, Users user) {
    super();
    this.otp = otp;
    this.users = user;
    this.isTrue = false;
    this.isDeleted = false;
    this.attemptCount = 0;
    this.expirationTime = this.getTokenExpirationTime();
  }

  public VerificationOtp() {

  }


  public Date getTokenExpirationTime() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(new Date().getTime());
    calendar.add(Calendar.MINUTE, EXPRATION_TIME);
    Date date = new Date(calendar.getTime().getTime());
    return date;
  }
}
