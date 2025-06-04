package com.ambrosezen.weblearnenglish.services.impl;


import com.ambrosezen.weblearnenglish.config.ResponseUtil;
import com.ambrosezen.weblearnenglish.converter.GenericConverter;
import com.ambrosezen.weblearnenglish.entities.Users;
import com.ambrosezen.weblearnenglish.repository.UserRepository;
import com.ambrosezen.weblearnenglish.services.EmailService;
import com.ambrosezen.weblearnenglish.services.utilities.ResponseMessage;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final UserRepository userRepository;
  private final GenericConverter genericConverter;


  @Value("${spring.mail.username}")
  private String username;

  @Value("${spring.mail.password}")
  private String password;

  @Value("${spring.mail.host}")
  private String host;

  public ResponseEntity<?> sendEmail(String emailResponse, String subject, String content) {
    try {
      if (!isValidEmail(emailResponse)) {
        return ResponseUtil.error(ResponseMessage.FORM_EMAIL_INVALID, ResponseMessage.OPERATION_FAILED, HttpStatus.BAD_REQUEST);
      }

      Users user = userRepository.findUserByEmail(emailResponse);
      if (user == null) {
        return ResponseUtil.error(ResponseMessage.EMAIL_EXISTS, ResponseMessage.OPERATION_FAILED, HttpStatus.BAD_REQUEST);
      }
      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", "587");

      Session session = Session.getInstance(props, new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(username, password);
        }
      });

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailResponse));
      message.setSubject(subject);
      message.setContent(content, "text/html; charset=utf-8");

      Transport.send(message);
      return ResponseUtil.getObject(ResponseMessage.SENT_EMAIL_SUCCESS, ResponseMessage.CREATE_SUCCESSFULLY, HttpStatus.OK);
    }catch (Exception ex) {
      return ResponseUtil.error(ex.getMessage(), ResponseMessage.OPERATION_FAILED, HttpStatus.BAD_REQUEST);
    }
  }

  private boolean isValidEmail(String email) {
    if (email == null || email.isBlank()) return false;
    String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    return Pattern.matches(pattern, email);
  }

  private int generateOTP() {
    SecureRandom random = new SecureRandom();
    return 100000 + random.nextInt(900000);
  }

}
