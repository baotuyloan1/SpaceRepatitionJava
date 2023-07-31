package com.example.dto.user.learn;

import java.util.Set;

import com.example.dto.user.TypeLearnRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BAO 7/20/2023
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLearnRes {
  private long vocabularyId;
  private String word;
  private String meaningWord;
  private String type;
  private String ipa;
  private String sentence;
  private String meaningSentence;
  private String audioSentence;
  private String audioWord;
  private String img;
  private Set<TypeLearnRes> learnTypes;
}
