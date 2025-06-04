package com.ambrosezen.weblearnenglish.dto;

import com.ambrosezen.weblearnenglish.converter.GenericConverter;
import com.ambrosezen.weblearnenglish.entities.enums.Gender;
import com.ambrosezen.weblearnenglish.entities.enums.Role;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
  private UUID userId;
  private String fullName;
  private String userName;
  private String email;
  private String Avatar;
  private Gender gender;
  private Date dateOfBirth;
  private Date createdAt;
  private Date updatedAt;
  private Role role;
  private String fcmToken;
  private boolean isDeleted;
  private boolean isEnabled;
  private List<UUID> userExamsIds;
  private List<UUID> resourcesIds;
  private List<UUID> userVocabularyIds;
  private List<UUID> translationsIds;
  private List<UUID> grammarsIds;
}
