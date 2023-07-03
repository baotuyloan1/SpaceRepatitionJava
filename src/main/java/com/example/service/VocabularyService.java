package com.example.service;

import com.example.entity.Vocabulary;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    List<Vocabulary> getVocabularyNotLearnedByUserId(long id);
}
