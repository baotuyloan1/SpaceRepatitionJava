package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author BAO 7/8/2023
 */

@Entity
@Table(name = "question")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "nvarchar")
    private String question;


    @JsonIgnoreProperties("question")
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "right_answer_id")
    private Answer answer;

    @JsonIgnoreProperties("question")
    @ManyToOne
    @JoinColumn(name = "id_vocabulary")
    private Vocabulary vocabulary;



    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;




}
