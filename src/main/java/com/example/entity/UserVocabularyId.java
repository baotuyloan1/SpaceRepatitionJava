package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

/**
 * @author BAO 7/3/2023
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserVocabularyId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "vocabulary_id")
    private Long vocabularyId;


}
