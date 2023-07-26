package com.example.service.impl;

import com.example.config.PropertiesConfig;
import com.example.mapper.VocabularyMapper;
import com.example.repository.UserRepository;
import com.example.service.UserVocabularyService;
import com.example.service.VocabularyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author ADMIN
 * 7/26/2023
 */class UserVocabularyServiceImplTest {

     @Mock
     private UserRepository userRepository;
     private AutoCloseable autoCloseable;
     private UserVocabularyService underTest;
     private VocabularyService vocabularyService;
     private PropertiesConfig env;
     private VocabularyMapper vocabularyMapper;

//    @BeforeEach
//    void setUp() {
//        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
//        underTest = new UserVocabularyServiceImpl(userRepository, vocabularyService,env,vocabularyMapper,"");
//      }

    @Test
    void canSaveNewLearnedVocabulary() {
      }

    @Test
    @Disabled
    void updateLearnedVocabulary() {
      }

    @Test
    @Disabled
    void getNextWordToReview() {
      }

    @Test
    @Disabled
    void getVocabulariesByTopicId() {
      }

    @Test
    void saveNewVocabulary() {
      }
}