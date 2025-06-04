package com.ambrosezen.weblearnenglish.dto.request;


import com.ambrosezen.weblearnenglish.entities.enums.Gender;
import com.ambrosezen.weblearnenglish.entities.enums.Role;
import java.util.Date;
import lombok.Data;

@Data
public class SignUp {
    private String email;
    private String username;
    private String fullname;
    private String avatar;
    private Date dateOfBirth;
    private Gender gender;
    private Role role;
    private String fcmtoken;
}
