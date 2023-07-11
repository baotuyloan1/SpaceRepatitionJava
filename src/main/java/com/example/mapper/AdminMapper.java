package com.example.mapper;

import com.example.dto.admin.VocabularyAdminResponse;
import com.example.entity.Question;
import com.example.entity.Vocabulary;
import org.springframework.stereotype.Component;

/**
 * @author BAO 7/11/2023
 */
@Component
public class AdminMapper {

    public VocabularyAdminResponse vocabularyToVocabularyAdminResponse(Vocabulary vocabulary){
        VocabularyAdminResponse vocabularyAdminResponse = new VocabularyAdminResponse();
    vocabularyAdminResponse.setId(vocabulary.getId());
    vocabularyAdminResponse.setIPA(vocabulary.getIPA());
    vocabularyAdminResponse.setImg(vocabulary.getImg());
    vocabularyAdminResponse.setAudioWord(vocabulary.getAudioWord());
    vocabularyAdminResponse.setAudioSentence(vocabulary.getAudioSentence());
    vocabularyAdminResponse.setAddedDate(vocabulary.getAddedDate());
    vocabularyAdminResponse.setQuestion(vocabulary.getQuestion());
    return null;
    }


}
