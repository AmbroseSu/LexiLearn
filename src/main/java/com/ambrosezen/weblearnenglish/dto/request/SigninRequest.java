package com.ambrosezen.weblearnenglish.dto.request;

import lombok.Data;

@Data
public class SigninRequest {
  private String username;
  private String password;
}
