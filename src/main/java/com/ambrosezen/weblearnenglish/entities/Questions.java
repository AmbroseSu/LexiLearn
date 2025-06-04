package com.ambrosezen.weblearnenglish.entities;

import com.ambrosezen.weblearnenglish.entities.enums.QuestionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "tbl_questions")
public class Questions {
  @Id
  @Column(name = "question_id", updatable = false, nullable = false)
  private UUID questionId;
  @PrePersist
  public void generateId() {
    if (questionId == null) {
      questionId = UUID.randomUUID();
    }
  }
  private String content;
  private String options;
  private String correctAnswer;
  private String explanation;
  private QuestionType questionType;

  @ManyToOne
  @JoinColumn(name = "exam_id")
  @ToString.Include
  private Exams exams;

  @ManyToOne
  @JoinColumn(name = "grammar_id")
  @ToString.Include
  private Grammar grammar;

}
