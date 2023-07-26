package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BAO 7/3/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_vocabulary")
@Entity
public class UserVocabulary {

  @EmbeddedId private UserVocabularyId id;

  @ManyToOne
  @MapsId("id")
  @JsonIgnore
  @JoinColumn(name = "id_user", referencedColumnName = "id")
  private User user;

  @ManyToOne
  @MapsId("id")
  @JoinColumn(name = "id_vocabulary", referencedColumnName = "id")
  @JsonIgnoreProperties("question")
  private Vocabulary vocabulary;

  @Column(name = "submit_date", columnDefinition = "datetime")
  private Date submitDate;

  @Column(name = "ef", columnDefinition = "DECIMAL(4,3)")
  private float ef;

  @Column(name = "review_date", columnDefinition = "datetime")
  private Date reviewDate;

  @Column(name = "q", columnDefinition = "tinyint")
  private short q;

  @Column(name = "count_learn")
  private int countLearn;


  public UserVocabulary(User user, Vocabulary vocabulary) {
    this.id = new UserVocabularyId(user.getId(), vocabulary.getId());
  }
}
