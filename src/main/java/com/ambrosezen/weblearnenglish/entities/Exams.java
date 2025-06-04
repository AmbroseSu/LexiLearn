package com.ambrosezen.weblearnenglish.entities;

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
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_exams")
public class Exams {
  @Id
  @Column(name = "exam_id", updatable = false, nullable = false)
  private UUID examId;
  @PrePersist
  public void generateId() {
    if (examId == null) {
      examId = UUID.randomUUID();
    }
  }
  private String title;
  private String type;
  private String level;
  private Date createdAt;
  private int duration;

  @OneToMany(mappedBy = "exams", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<UserExam> userExams;

  @OneToMany(mappedBy = "exams", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Questions> questions;


}
