package com.ambrosezen.weblearnenglish.controller;

import com.ambrosezen.weblearnenglish.dto.request.SignUp;
import com.ambrosezen.weblearnenglish.dto.request.SigninRequest;
import com.ambrosezen.weblearnenglish.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  @PostMapping("/signin")
  public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest){
    return authenticationService.signin(signinRequest);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody SignUp signUp) {
    return authenticationService.createUser(signUp);
  }
}
