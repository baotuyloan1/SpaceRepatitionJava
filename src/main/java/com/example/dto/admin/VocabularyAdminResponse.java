package com.example.dto.admin;

import com.example.entity.Question;
import com.example.entity.Topic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author BAO 7/11/2023
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyAdminResponse {



    private long id;


    private String word;

    private String IPA;

    private String meaningWord;

    private String type;

    private String sentence;

    private String meaningSentence;


    private String audioSentence;


    private String audioWord;


    private Date addedDate;


    private String img;


    private Topic topic;


    private List<Question> question;
}
