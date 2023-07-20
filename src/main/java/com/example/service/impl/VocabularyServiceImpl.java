package com.example.service.impl;

import com.example.entity.*;
import com.example.exception.CustomerException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AnswerRepository;
import com.example.repository.UserRepository;
import com.example.repository.UserVocabularyRepository;
import com.example.repository.VocabularyRepository;
import com.example.security.services.UserDetailsImpl;
import com.example.service.VocabularyService;
import com.example.utils.FileUtils;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author BAO 6/29/2023
 */
@Service
public class VocabularyServiceImpl implements VocabularyService {

  private final VocabularyRepository vocabularyRepository;

  private final AnswerRepository answerRepository;

  private final UserRepository userRepository;

  private final UserVocabularyRepository userVocabularyRepository;
  private final FileUtils filenameUtils;

  @Value("${dir.resource.audioWord}")
  private String dirAudioWord;

  @Value("${dir.resource.audioSentence}")
  private String dirAudioSentence;

  @Value("${dir.resource.imgWord}")
  private String dirImgWord;

  public VocabularyServiceImpl(
      VocabularyRepository vocabularyRepository,
      AnswerRepository answerRepository,
      UserRepository userRepository,
      UserVocabularyRepository userVocabularyRepository,
      FileUtils filenameUtils) {
    this.vocabularyRepository = vocabularyRepository;
    this.answerRepository = answerRepository;
    this.userRepository = userRepository;
    this.userVocabularyRepository = userVocabularyRepository;
    this.filenameUtils = filenameUtils;
  }

  @Transactional
  @Override
  public Vocabulary createWord(
      Vocabulary vocabulary,
      MultipartFile audioWord,
      MultipartFile audioSentence,
      MultipartFile img) {
    try {
      vocabulary.setAddedDate(new Date());
      Vocabulary savedWord = vocabularyRepository.save(vocabulary);

      // saveAudioWord, có nhiều extentions file mp3 khác nhau
      String audioWordFileName =
          filenameUtils.saveFile(audioWord, savedWord.getId(), dirAudioWord, "word");
      savedWord.setAudioWord(audioWordFileName);

      // saveAudioSentence
      String audioSentenceFileName =
          filenameUtils.saveFile(audioSentence, savedWord.getId(), dirAudioSentence, "sentence");
      savedWord.setAudioSentence(audioSentenceFileName);

      // saveImage
      String imageFileName = filenameUtils.saveFile(img, savedWord.getId(), dirImgWord, "word");
      savedWord.setImg(imageFileName);

      return vocabularyRepository.save(savedWord);
    } catch (IOException e) {
      throw new CustomerException(e, "Something went wrong");
    }
  }

  @Override
  public Vocabulary getById(Long id) {
    return vocabularyRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(false, "Audio not found"));
  }

  @Transactional
  @Override
  public Vocabulary update(Vocabulary vocabulary, MultipartFile audio) {
    return vocabularyRepository.save(vocabulary);
  }

  @Override
  public void deleteAudio(Long id) {}

  @Transactional
  @Override
  public List<Vocabulary> getAllVocabulary() {
    List<Vocabulary> vocabularyList = vocabularyRepository.findAll();
    for (Vocabulary vocabulary : vocabularyList) {
      vocabulary.setQuestion(null);
    }
    return vocabularyList;
  }

  //  @Transactional
  @Override
  public List<Map<String, Object>> getLearnVocabulary(int topicId) {
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    /**
     * Tại sao không gọi chung multi join fetch trong 1 câu query ?. Sẽ bị lỗi Cartesian product.
     */
    List<Vocabulary> vocabularyList =
        vocabularyRepository.findVocabulariesNotLearnedByUserIdInTopicId(
            userDetails.getId(), topicId);

    List<Map<String, Object>> objectWords = new LinkedList<>();

    for (Vocabulary vocabulary : vocabularyList) {

      Map<String, Object> word = new LinkedHashMap<>();
      word.put("id", vocabulary.getId());
      word.put("word", vocabulary);

      List<Object> learningTypes = new LinkedList<>();

      Map<String, Object> map = new LinkedHashMap<>();

      /** Random các loại câu hỏi. Chỉ lấy 1 câu hỏi của mỗi từ */
      List<Question> questionList = vocabulary.getQuestion();
      int sizeList = questionList.size();
      if (sizeList > 0) {
        int indexQuestion = (int) Math.floor(Math.random() * sizeList);
        Question currentQuestion = questionList.get(indexQuestion);
        map.put("id", 1);
        map.put("type", "select");
        map.put("question", currentQuestion);

        /** Selection type */
        // dùng foreach lấy từ bảng ra các đáp án của question id
        List<Answer> listAnswer = answerRepository.findByQuestionId(currentQuestion.getId());
        map.put("answers", listAnswer);
        map.put("rightQuestionId", currentQuestion.getAnswer().getId());
        learningTypes.add(map);
      }
      /** Nghe phát âm từ và điền lại từ cho đúng */
      Map<String, Object> listenMap = new LinkedHashMap<>();
      listenMap.put("id", 2);
      listenMap.put("type", "listen");
      learningTypes.add(listenMap);

      /** Hiển thị nghĩa của từ và ghi lại từ */
      Map<String, Object> meanMap = new LinkedHashMap<>();
      meanMap.put("id", 3);
      meanMap.put("type", "mean");
      learningTypes.add(meanMap);
      word.put("learningTypes", learningTypes);

      objectWords.add(word);
    }
    return objectWords;
  }

  @Override
  public List<Vocabulary> findWordsNotLearnedByUserIdInTopicId(Long userId, int topicId) {
    return vocabularyRepository.findVocabulariesNotLearnedByUserIdInTopicId(userId, topicId);
  }

  @Override
  public List<Vocabulary> getLearnedWordInTopicByUserId(Topic topic, Long userId) {
    return vocabularyRepository.findLearnedWordByTopicAndUserId(topic.getId(), userId);
  }
}
