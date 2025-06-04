package com.ambrosezen.weblearnenglish.entities;

import com.ambrosezen.weblearnenglish.entities.enums.UserVocabStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "tbl_user_vocabulary")
public class UserVocabulary {
  @Id
  @Column(name = "user_vocabulary_id", updatable = false, nullable = false)
  private UUID userVocabularyId;
  @PrePersist
  public void generateId() {
    if (userVocabularyId == null) {
      userVocabularyId = UUID.randomUUID();
    }
  }
  private UserVocabStatus status;
  private Date lastReviewedAt;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @ToString.Include
  private Users users;
  @ManyToOne
  @JoinColumn(name = "vocabulary_id")
  @ToString.Include
  private Vocabulary vocabulary;
}
