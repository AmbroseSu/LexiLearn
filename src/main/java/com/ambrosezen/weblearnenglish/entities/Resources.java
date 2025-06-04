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
@Table(name = "tbl_resources")
public class Resources {
  @Id
  @Column(name = "rresource_id", updatable = false, nullable = false)
  private UUID resourceId;
  @PrePersist
  public void generateId() {
    if (resourceId == null) {
      resourceId = UUID.randomUUID();
    }
  }
  private String title;
  private String fileUrl;
  private Date CreatedAt;
  private String Category;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @ToString.Include
  private Users users;

}
