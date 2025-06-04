package com.ambrosezen.weblearnenglish.entities;


import com.ambrosezen.weblearnenglish.entities.enums.Gender;
import com.ambrosezen.weblearnenglish.entities.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@Table(name = "tbl_users")
public class Users implements UserDetails {
  @Id
  @Column(name = "user_id", updatable = false, nullable = false)
  private UUID userId;
  @PrePersist
  public void generateId() {
    if (userId == null) {
      userId = UUID.randomUUID();
    }
  }
  private String fullName;
  private String userName;
  private String email;
  private String password;
  private String avatar;
  private Gender gender;
  private Date dateOfBirth;
  private Date createdAt;
  private Date updatedAt;
  private Role role;
  private String fcmToken;
  private boolean isDeleted;
  private boolean isEnabled = false;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<UserExam> userExams;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Resources> resources;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<UserVocabulary> userVocabulary;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Translation> translations;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Grammar> grammars;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return userName;
  }



  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  @Override
  public boolean isEnabled() {
    return true;
  }
  public boolean getIsEnabled() {
    return isEnabled;
  }
}
