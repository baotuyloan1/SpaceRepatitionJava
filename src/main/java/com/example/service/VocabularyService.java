package com.example.service;

import com.example.entity.VocabularyEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author BAO
 * 6/29/2023
 */
public interface VocabularyService {
    VocabularyEntity createWord(VocabularyEntity vocabularyEntity, MultipartFile audioWord, MultipartFile audioSentence, MultipartFile img);

    VocabularyEntity getById(Long id);

    VocabularyEntity update(VocabularyEntity vocabularyEntity, MultipartFile audio);

    void deleteAudio(Long id);

    List<VocabularyEntity> getAllAudio();
}
