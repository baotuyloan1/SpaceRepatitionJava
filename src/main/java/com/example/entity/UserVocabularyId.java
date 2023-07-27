package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.*;

/**
 * @author BAO 7/3/2023
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
public class UserVocabularyId implements Serializable {
  @Column(name = "id_user")
  private long userId;

  @Column(name = "id_vocabulary")
  private long vocabularyId;
}
