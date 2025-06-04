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
@Table(name = "tbl_translation")
public class Translation {
  @Id
  @Column(name = "translation_id", updatable = false, nullable = false)
  private UUID translationId;
  @PrePersist
  public void generateId() {
    if (translationId == null) {
      translationId = UUID.randomUUID();
    }
  }
  private String sourceText;
  private String TargetText;
  private Date CreatedAt;
  private boolean isCorrected;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @ToString.Include
  private Users users;
}
