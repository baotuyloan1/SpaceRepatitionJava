package com.example.dto.admin;

import com.example.entity.Question;
import com.example.entity.Topic;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BAO 7/11/2023
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminVocabularyRes {

  private long id;
  private String word;
  private String ipa;
  private String meaningWord;
  private String type;
  private String sentence;
  private String meaningSentence;
  private String audioSentence;
  private String audioWord;
  private String addedDate;
  private String img;
  private AdminTopicRes topic;
}
