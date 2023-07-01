package com.example.service;

import com.example.entity.WordEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author BAO
 * 6/29/2023
 */
public interface WordService {
    WordEntity createWord(WordEntity wordEntity, MultipartFile audioWord, MultipartFile audioSentence);

    WordEntity getById(Long id);

    WordEntity update(WordEntity wordEntity, MultipartFile audio);

    void deleteAudio(Long id);

    List<WordEntity> getAllAudio();
}
