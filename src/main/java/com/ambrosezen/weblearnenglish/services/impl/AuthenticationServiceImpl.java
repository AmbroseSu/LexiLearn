package com.ambrosezen.weblearnenglish.services.impl;



import com.ambrosezen.weblearnenglish.config.ResponseUtil;
import com.ambrosezen.weblearnenglish.converter.GenericConverter;
import com.ambrosezen.weblearnenglish.dto.UserDTO;
import com.ambrosezen.weblearnenglish.dto.request.SignUp;
import com.ambrosezen.weblearnenglish.dto.request.SigninRequest;
import com.ambrosezen.weblearnenglish.dto.response.JwtAuthenticationResponse;
import com.ambrosezen.weblearnenglish.entities.Users;
import com.ambrosezen.weblearnenglish.repository.UserRepository;
import com.ambrosezen.weblearnenglish.repository.VerificationOtpRepository;
import com.ambrosezen.weblearnenglish.services.AuthenticationService;
import com.ambrosezen.weblearnenglish.services.EmailService;
import com.ambrosezen.weblearnenglish.services.JWTService;
import com.ambrosezen.weblearnenglish.services.UserService;
import com.ambrosezen.weblearnenglish.services.utilities.ResponseMessage;
import jakarta.validation.ConstraintViolationException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final GenericConverter genericConverter;
    private final VerificationOtpRepository verificationTokenRepository;


    @Override
    public ResponseEntity<?> signin(SigninRequest signinRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
        } catch (Exception e) {
            return ResponseUtil.error(ResponseMessage.EMAIL_PASSWORD_INVALID,
                ResponseMessage.OPERATION_FAILED, HttpStatus.BAD_REQUEST);
        }


        var user = userRepository.findByUsername(signinRequest.getUsername()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        UserDTO userDTO = (UserDTO) genericConverter.toDTO(user, UserDTO.class);

        jwtAuthenticationResponse.setUserDTO(userDTO);
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return ResponseUtil.getObject(jwtAuthenticationResponse, ResponseMessage.CREATE_SUCCESSFULLY, HttpStatus.OK);
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> createUser(SignUp signUp){
        try {
            Users user = userRepository.findUserByEmail(signUp.getEmail());
            if (user != null) {
                return ResponseUtil.error(ResponseMessage.EMAIL_EXISTS, ResponseMessage.OPERATION_FAILED, HttpStatus.BAD_REQUEST);
            }
            Users checkUsername = userRepository.findUserByUsername(signUp.getUsername());
            if (checkUsername != null) {
                return ResponseUtil.error(ResponseMessage.USERNAME_EXISTS, ResponseMessage.OPERATION_FAILED, HttpStatus.BAD_REQUEST);
            }
            if (!IsValidEmail(signUp.getEmail())) {
                return ResponseUtil.error(ResponseMessage.FORM_EMAIL_INVALID, ResponseMessage.OPERATION_FAILED, HttpStatus.BAD_REQUEST);
            }
            if (!isValidUsername(signUp.getUsername())) {
                return ResponseUtil.error(ResponseMessage.FORM_USERNAME_INVALID, ResponseMessage.OPERATION_FAILED, HttpStatus.BAD_REQUEST);
            }
            var plainPassword = GenerateRandomString(10);
            var encodedPassword = passwordEncoder.encode(plainPassword);

            Users newUser = (Users) genericConverter.toEntity(signUp, Users.class);
            newUser.setPassword(encodedPassword);
            newUser.setEnabled(false);
            newUser.setCreatedAt(Date.from(Instant.now()));
            newUser.setUpdatedAt(Date.from(Instant.now()));
            userRepository.save(newUser);

            String emailContent = String.format(
                "<p>Dear %s,</p>" +
                "<p>Thank you for signing up. Your account has been created successfully.</p>" +
                    "<p>Your username is: <strong>%s</strong></p>" +
                "<p>Your temporary password is: <strong>%s</strong></p>" +
                "<p>Please log in and change your password as soon as possible.</p>" +
                "<p>Best regards,</p>" +
                "<p>Web Learn English Team</p>",
                newUser.getUsername(), newUser.getEmail(), plainPassword
            );

            boolean emailSent = false;
            int retryCount = 0;
            int maxRetries = 3;

            while (!emailSent && retryCount < maxRetries) {
                    ResponseEntity<?> emailResponse = emailService.sendEmail(newUser.getEmail(), "Welcome to Web Learn English", emailContent);
                    if (emailResponse.getStatusCode() == HttpStatus.OK) {
                        emailSent = true;
                        // Optionally, you can log the success or perform other actions
                    } else {
                        Thread.sleep(2000); // Wait before retrying
                        retryCount++;
                    }
            }
            if (!emailSent) {
                throw new RuntimeException("Send email failed after multiple attempts.");
            }
            UserDTO result = (UserDTO) genericConverter.toDTO(newUser, UserDTO.class);
            return ResponseUtil.getObject(result, ResponseMessage.CREATE_SUCCESSFULLY, HttpStatus.CREATED);
        }catch (Exception ex) {
            return ResponseUtil.error(ex.getMessage(), ResponseMessage.OPERATION_FAILED, HttpStatus.BAD_REQUEST);
        }
    }


    private static boolean IsValidEmail(String email)
    {
        if (email == null || email.isEmpty())
            return false;
        var pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(pattern, email);
    }

    public static boolean isValidUsername(String username) {
        if (username == null) return false;
        var pattern = "^[a-zA-Z][a-zA-Z0-9._]{4,19}$";
        return Pattern.matches(pattern, username);
    }

    private static String GenerateRandomString(int length)
    {
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String specialCharacters = "!@#$%^&*";

        var allCharacters = lowerCase + upperCase + numbers + specialCharacters;

        SecureRandom random = new SecureRandom();
        StringBuilder result = new StringBuilder(length);
        // Bắt buộc có 1 ký tự mỗi loại
        result.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        result.append(upperCase.charAt(random.nextInt(upperCase.length())));
        result.append(numbers.charAt(random.nextInt(numbers.length())));
        result.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));

        // Phần còn lại chọn ngẫu nhiên
        for (int i = 4; i < length; i++) {
            result.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
        }

        // Trộn chuỗi để tránh predictable pattern
        List<Character> chars = result.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.toList());
        Collections.shuffle(chars, random);

        StringBuilder finalString = new StringBuilder();
        chars.forEach(finalString::append);

        return finalString.toString();
    }

}
