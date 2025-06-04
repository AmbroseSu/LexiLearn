package com.ambrosezen.weblearnenglish.entities;

import com.ambrosezen.weblearnenglish.entities.enums.Category;
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
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_vocabulary")
public class Vocabulary {
  @Id
  @Column(name = "vocabulary_id", updatable = false, nullable = false)
  private UUID vocabularyId;
  @PrePersist
  public void generateId() {
    if (vocabularyId == null) {
      vocabularyId = UUID.randomUUID();
    }
  }
  private String word;
  private String meaning;
  private String pronunciation;
  private Category category;

  @OneToMany(mappedBy = "vocabulary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<UserVocabulary> userVocabulary;

  @OneToMany(mappedBy = "vocabulary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Sentences> sentences;

}
