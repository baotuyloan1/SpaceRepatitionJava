package com.example.mapper.impl;

import com.example.dto.admin.AdminAnswerRes;
import com.example.dto.admin.AdminQuestionRes;
import com.example.entity.Answer;
import com.example.entity.Question;
import com.example.mapper.QuestionMapper;
import com.example.mapper.VocabularyMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author BAO 7/20/2023
 */
@Component
@AllArgsConstructor
public class QuestionMapperImpl implements QuestionMapper {

  private final VocabularyMapper vocabularyMapper;



  @Override
  public AdminQuestionRes questionToAdminQuestionRes(Question question) {
    AdminQuestionRes questionRes = new AdminQuestionRes();
    questionRes.setQuestion(question.getQuestion());
    questionRes.setId(question.getId());
    questionRes.setVocabulary(
        vocabularyMapper.vocabularyToAdminVocabularyResponse(question.getVocabulary()));
    questionRes.setRightAnswer(answerToAdminAnswerRes(question.getRightAnswer()));
    questionRes.setAnswers(answersToAdminAnswersRes(question.getAnswers()));
    return questionRes;
  }

  @Override
  public List<AdminQuestionRes> questionsToAdminQuestionsRes(List<Question> questions) {
    return questions.stream().map(this::questionToAdminQuestionRes).toList();
  }

  public AdminAnswerRes answerToAdminAnswerRes(Answer answer) {
    AdminAnswerRes answerRes = new AdminAnswerRes();
    answerRes.setAnswer(answer.getAnswer());
    answerRes.setId(answer.getId());
    return answerRes;
  }

  public List<AdminAnswerRes> answersToAdminAnswersRes(List<Answer> answers) {
    return answers.stream().map(this::answerToAdminAnswerRes).toList();
  }
}
