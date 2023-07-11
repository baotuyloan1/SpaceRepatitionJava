package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author BAO
 * 6/29/2023
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "vocabulary")
public class    Vocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank
    @Column(name = "word")
    private String word;
    @NotBlank
    @Column(name = "IPA")
    private String IPA;
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

    @Column(name = "added_date",columnDefinition = "datetime")
    private Date addedDate;

    @Column(name = "img")
    private String img;

    @JsonIgnoreProperties("vocabulary")
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;


    @OneToMany(mappedBy = "vocabulary")
    private List<Question> question;



}
