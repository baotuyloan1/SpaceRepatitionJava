package com.example.mapper;

import com.example.dto.user.UserLearnRes;
import com.example.entity.Vocabulary;

import java.util.List;

/**
 * @author BAO 7/20/2023
 */
public interface VocabularyMapper {

    UserLearnRes vocabularyToUserLearnRes(Vocabulary vocabulary);

    List<UserLearnRes> vocabulariesToUserLearnRes(List<Vocabulary> vocabularies);
}
