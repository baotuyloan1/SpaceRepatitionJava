package com.example.service;

import com.example.dto.user.UserVocabularyRequest;
import com.example.entity.UserVocabulary;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * @author BAO 7/13/2023
 */
@Service
public interface UserVocabularyService {

  UserVocabulary saveNewLearnedVocabulary(long id);

  UserVocabulary updateLearnedVocabulary(UserVocabularyRequest userVocabularyRequest);

  List<Map<String, Object>> getNextWordToReview();
}
