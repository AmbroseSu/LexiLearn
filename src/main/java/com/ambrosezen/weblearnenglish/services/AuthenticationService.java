package com.ambrosezen.weblearnenglish.services;



import com.ambrosezen.weblearnenglish.dto.request.SignUp;
import com.ambrosezen.weblearnenglish.dto.request.SigninRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> signin(SigninRequest signinRequest);
    ResponseEntity<?> createUser(SignUp signUp);

    //ResponseEntity<?> signinGoogle(String email);

    //ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest);

    //public ResponseEntity<?> checkEmail(String email);

    //public String checkResetVerifyToken(String email, Long id);


    //public ResponseEntity<?> checkEmailForgotPassword(String email);

    //public ResponseEntity<?> changePassword(String email, String password);
    //public ResponseEntity<?> saveInforGoogle(SignUpGoogle signUpGoogle);
}
