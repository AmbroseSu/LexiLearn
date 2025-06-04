package com.ambrosezen.weblearnenglish.dto.response;


import com.ambrosezen.weblearnenglish.dto.UserDTO;
import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private UserDTO userDTO;
    private String token;
    private String refreshToken;
}
