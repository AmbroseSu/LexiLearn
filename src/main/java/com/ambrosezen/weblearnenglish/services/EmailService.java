package com.ambrosezen.weblearnenglish.services;

import org.springframework.http.ResponseEntity;

public interface EmailService {
  ResponseEntity<?> sendEmail(String emailResponse, String subject, String content);
}
