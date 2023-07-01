package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author BAO
 * 6/29/2023
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "words")
public class WordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 50)
    private String word;
    @Column(length = 200)

    private String IPA;
    private String meaningWord;
    private String type;
    private String sentence;
    private String meaningSentence;
    private String audioSentence;
    private String audioWord;

    private Date addedDate;

}
