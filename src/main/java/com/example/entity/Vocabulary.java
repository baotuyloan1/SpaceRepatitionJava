package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BAO 6/29/2023
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vocabulary")
public class Vocabulary {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @NotBlank
  @Column(name = "word")
  private String word;

  @NotBlank
  @Column(name = "IPA")
  private String ipa;

  @NotBlank
  @Column(name = "meaning_word", columnDefinition = "NVARCHAR")
  private String meaningWord;

  @NotBlank
  @Column(name = "type", length = 100)
  private String type;

  @NotBlank
  @Column(name = "sentence", columnDefinition = "TEXT")
  private String sentence;

  @NotBlank
  @Column(name = "meaning_sentence", columnDefinition = "NTEXT")
  private String meaningSentence;

  @Column(name = "audio_sentence")
  private String audioSentence;

  @Column(name = "audio_word")
  private String audioWord;

  @Column(name = "added_date", columnDefinition = "datetime")
  private Date addedDate;

  @Column(name = "img")
  private String img;

  @JsonIgnoreProperties("vocabulary")
  @ManyToOne
  @JoinColumn(name = "topic_id")
  private Topic topic;

  @OneToMany(mappedBy = "vocabulary", cascade = CascadeType.ALL)
  private List<Question> question;


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Vocabulary that = (Vocabulary) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
