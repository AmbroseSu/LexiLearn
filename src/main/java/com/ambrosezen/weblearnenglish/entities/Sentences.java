package com.ambrosezen.weblearnenglish.entities;

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
@Table(name = "tbl_sentences")
public class Sentences {
  @Id
  @Column(name = "sentence_id", updatable = false, nullable = false)
  private UUID sentenceId;
  @PrePersist
  public void generateId() {
    if (sentenceId == null) {
      sentenceId = UUID.randomUUID();
    }
  }
  private String content;
  private String translation;
  private boolean createdByAI;
  private Date createdAt;

  @ManyToOne
  @JoinColumn(name = "vocabulary_id")
  @ToString.Include
  private Vocabulary vocabulary;
}
