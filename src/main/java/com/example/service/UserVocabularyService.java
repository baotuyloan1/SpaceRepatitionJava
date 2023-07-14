package com.example.service;

import com.example.dto.user.NextTimeReviewResonse;
import com.example.dto.user.UserVocabularyRequest;
import com.example.entity.UserVocabulary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author BAO 7/13/2023
 */
@Service
public interface UserVocabularyService {

    UserVocabulary saveNewLearnedVocabulary(long id)
;

    UserVocabulary updateLearnedVocabulary(UserVocabularyRequest userVocabularyRequest);

    List<UserVocabulary> getNextTimeToReview();
}
