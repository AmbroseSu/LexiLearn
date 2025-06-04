package com.ambrosezen.weblearnenglish.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "tbl_grammar")
public class Grammar {
  @Id
  @Column(name = "grammar_id", updatable = false, nullable = false)
  private UUID grammarId;
  @PrePersist
  public void generateId() {
    if (grammarId == null) {
      grammarId = UUID.randomUUID();
    }
  }
  private String title;
  private String content;
  private String level;
  private String exercises;

  @OneToMany(mappedBy = "grammar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Questions> questions;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @ToString.Include
  private Users users;
}
