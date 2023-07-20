package com.example.service;

import com.example.entity.Topic;
import com.example.entity.Vocabulary;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author BAO
 * 6/29/2023
 */
public interface VocabularyService {
    Vocabulary createWord(Vocabulary vocabulary, MultipartFile audioWord, MultipartFile audioSentence, MultipartFile img);

    Vocabulary getById(Long id);

    Vocabulary update(Vocabulary vocabulary, MultipartFile audio);

    void deleteAudio(Long id);

    List<Vocabulary> getAllVocabulary();


    List<Map<String, Object>> getLearnVocabulary(int topicId);

     List<Vocabulary> findWordsNotLearnedByUserIdInTopicId(Long userId, int topicId);

    List<Vocabulary> getLearnedWordInTopicByUserId(Topic topic, Long userId);


}
