package com.example.entity;

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
  @MapsId("userId")
  @JoinColumn(name = "id_user")
  private User user;

  @ManyToOne
  @MapsId("vocabularyId")
  @JoinColumn(name = "id_vocabulary")
  private Vocabulary vocabulary;

  @Column(name = "submit_date", columnDefinition = "datetime")
  private Date submitDate;

  @Column(name = "current_FE")
  private float currentFE;

  private Date endDate;

  private short countFalse;
  private int timeRepetition;

  public UserVocabulary(User user, Vocabulary vocabulary) {
    this.id = new UserVocabularyId(user.getId(), vocabulary.getId());
  }
}
