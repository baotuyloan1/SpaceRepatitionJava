package com.example.mapper.impl;

import com.example.dto.user.UserLearnRes;
import com.example.entity.Vocabulary;
import com.example.mapper.VocabularyMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author BAO 7/20/2023
 */
@Component
public class VocabularyMapperImpl implements VocabularyMapper {
    @Override
    public UserLearnRes vocabularyToUserLearnRes(Vocabulary vocabulary) {
        UserLearnRes userLearnRes = new UserLearnRes();
        userLearnRes.setVocabularyId(vocabulary.getId());
        userLearnRes.setImg(vocabulary.getImg());
        userLearnRes.setSentence(vocabulary.getSentence());
        userLearnRes.setAudioWord(vocabulary.getAudioWord());
        userLearnRes.setAudioSentence(vocabulary.getAudioSentence());
        userLearnRes.setWord(vocabulary.getWord());
        userLearnRes.setMeaningWord(vocabulary.getMeaningWord());
        userLearnRes.setMeaningSentence(vocabulary.getMeaningSentence());
        return userLearnRes;
    }

    @Override
    public List<UserLearnRes> vocabulariesToUserLearnRes(List<Vocabulary> vocabularies) {
        return null;
    }
}
