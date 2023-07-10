package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author BAO 7/10/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestDto {

    private int vocabularyId;

    private String question;
    private short indexRightAnswer;
    private List<String> answers;


}
