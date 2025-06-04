package com.ambrosezen.weblearnenglish.entities;

import com.ambrosezen.weblearnenglish.entities.Exams;
import com.ambrosezen.weblearnenglish.entities.Users;
import com.fasterxml.jackson.databind.JsonNode;
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
@Table(name = "tbl_user_exam")
public class UserExam {
  @Id
  @Column(name = "user_exam_id", updatable = false, nullable = false)
  private UUID userExamId;
  @PrePersist
  public void generateId() {
    if (userExamId == null) {
      userExamId = UUID.randomUUID();
    }
  }
  private float score;
  private Date CompletedAt;
  private String answers;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @ToString.Include
  private Users users;
  @ManyToOne
  @JoinColumn(name = "exam_id")
  @ToString.Include
  private Exams exams;
}
