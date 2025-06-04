package com.ambrosezen.weblearnenglish.services;

import com.ambrosezen.weblearnenglish.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

  UserDetailsService userDetailsService();


}
