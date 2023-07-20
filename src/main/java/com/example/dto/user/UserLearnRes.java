package com.example.dto.user;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BAO 7/20/2023
 */
@Getter
@Setter
public class UserLearnRes {
    private long vocabularyId;
    private String word;
    private String meaningWord;
    private String sentence;
    private String meaningSentence;
    private String audioSentence;
    private String audioWord;
    private String img;
  /**
   * 3 dạng
   *
   * [
   *      {type : "info",
   *
   *      },
   *      {
   *          type: "select",
   *          question: "Chicken is an ..... ?"
   *          rightAnswer : 1
   *          answers :[
   *            {
   *                id: 1,
   *                answer: "animal"
   *            },
   *             {
   *                     id: 1,
   *                     answer: "animal"
   *                 },
   *              {
   *                  id: 2,
   *                  answer: "dog",
   *              }
   *          ]
   *      },
   *      {
   *          type: "mean"
   *      },
   *      {
   *          type: "listen"
   *      }
   * ]
   */
  private Set<TypeLearnRes> learnTypes;
}
