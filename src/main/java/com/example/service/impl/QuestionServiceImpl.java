package com.example.service.impl;

import com.example.dto.QuestionRequestDto;
import com.example.entity.Answer;
import com.example.entity.Question;
import com.example.entity.Vocabulary;
import com.example.exception.CustomerException;
import com.example.repository.AnswerRepository;
import com.example.repository.QuestionRepository;
import com.example.service.QuestionService;
import jakarta.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author BAO 7/8/2023
 */
@Service
public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository questionRepository;

  private final AnswerRepository answerRepository;

  public QuestionServiceImpl(
      QuestionRepository questionRepository, AnswerRepository answerRepository) {
    this.questionRepository = questionRepository;
    this.answerRepository = answerRepository;
  }

  @Transactional
  @Override
  public Question save(QuestionRequestDto questionRequestDto) {
    Question question = new Question();
    question.setQuestion(questionRequestDto.getQuestion());
    Vocabulary vocabulary = new Vocabulary();
    vocabulary.setId(questionRequestDto.getVocabularyId());
    question.setVocabulary(vocabulary);
    Question savedQuestion = questionRepository.save(question);
    List<Answer> answerList = new LinkedList<>();
    for (String answer : questionRequestDto.getAnswers()) {
      answerList.add(new Answer(null, answer, savedQuestion));
    }
    List<Answer> savedAnswers = answerRepository.saveAll(answerList);
    question.setAnswer(savedAnswers.get(questionRequestDto.getIndexRightAnswer()));
    question.setAnswers(savedAnswers);

    return questionRepository.save(question);
  }

  @Override
  public List<Question> listQuestion() {
    return questionRepository.findAll();
  }

  @Override
  public void deleteQuestion(Long questionId) {
    questionRepository.deleteById(questionId);
    if (questionRepository.existsById(questionId)){
      throw  new CustomerException("Delete Question fail, some thing went wrong");
    }

  }

  @Override
  public Question findById(Long questionId) {
    return questionRepository.findById(questionId).orElseThrow(() ->  new CustomerException("Question not found"));
  }


}
