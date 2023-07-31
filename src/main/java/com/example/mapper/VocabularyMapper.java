package com.example.mapper;

import com.example.dto.admin.AdminVocabularyRes;
import com.example.dto.user.learn.UserLearnRes;
import com.example.dto.user.learn.UserReviewRes;
import com.example.dto.user.review.UserNextWordsReq;
import com.example.entity.UserVocabulary;
import com.example.entity.Vocabulary;

import java.util.List;

/**
 * @author BAO 7/20/2023
 */
public interface VocabularyMapper {

    UserLearnRes vocabularyToUserLearnRes(Vocabulary vocabulary);
    UserNextWordsReq vocabularyToUserNextWordsReq( UserVocabulary userVocabulary);

    List<UserLearnRes> vocabulariesToUserLearnRes(List<Vocabulary> vocabularies);

    AdminVocabularyRes vocabularyToAdminVocabularyResponse(Vocabulary vocabulary);

    List<AdminVocabularyRes> vocabulariesToAdminVocabulariesRes(List<Vocabulary> vocabularies);
}
