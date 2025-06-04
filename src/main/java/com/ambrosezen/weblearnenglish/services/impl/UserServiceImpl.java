package com.ambrosezen.weblearnenglish.services.impl;

import com.ambrosezen.weblearnenglish.converter.GenericConverter;
import com.ambrosezen.weblearnenglish.dto.request.SignUp;
import com.ambrosezen.weblearnenglish.dto.response.ResponseDTO;
import com.ambrosezen.weblearnenglish.repository.UserRepository;
import com.ambrosezen.weblearnenglish.repository.VerificationOtpRepository;
import com.ambrosezen.weblearnenglish.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final VerificationOtpRepository verificationOtpRepository;
  private final GenericConverter genericConverter;

  @Override
  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            //.map(UserSignupDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
      }
    };
  }







}
