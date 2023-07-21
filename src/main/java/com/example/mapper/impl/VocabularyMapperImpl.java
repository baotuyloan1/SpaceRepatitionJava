package com.example.mapper.impl;

import com.example.dto.admin.AdminVocabularyRes;
import com.example.dto.user.UserLearnRes;
import com.example.entity.Vocabulary;
import com.example.mapper.TopicMapper;
import com.example.mapper.VocabularyMapper;
import com.example.utils.DateTimeUtils;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * @author BAO 7/20/2023
 */
@Component
public class VocabularyMapperImpl implements VocabularyMapper {

  private final DateTimeUtils dateTimeUtils;
  private final TopicMapper topicMapper;

  public VocabularyMapperImpl(DateTimeUtils dateTimeUtils, TopicMapper topicMapper) {
    this.dateTimeUtils = dateTimeUtils;
    this.topicMapper = topicMapper;
  }

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

  @Override
  public AdminVocabularyRes vocabularyToAdminVocabularyResponse(Vocabulary vocabulary) {
    AdminVocabularyRes vocabularyRes = new AdminVocabularyRes();
    vocabularyRes.setIpa(vocabulary.getIpa());
    vocabularyRes.setId(vocabulary.getId());
    vocabularyRes.setImg(vocabulary.getImg());
    vocabularyRes.setAddedDate(dateTimeUtils.formatDateToString(vocabulary.getAddedDate()));
    vocabularyRes.setSentence(vocabulary.getSentence());
    vocabularyRes.setWord(vocabulary.getWord());
    vocabularyRes.setAudioWord(vocabulary.getAudioWord());
    vocabularyRes.setMeaningWord(vocabulary.getMeaningWord());
    vocabularyRes.setAudioSentence(vocabulary.getAudioSentence());
    vocabularyRes.setMeaningSentence(vocabulary.getMeaningSentence());
    vocabularyRes.setType(vocabulary.getType());
    vocabularyRes.setTopic(topicMapper.topicToAdminTopicRes(vocabulary.getTopic()));
    return vocabularyRes;
  }

  @Override
  public List<AdminVocabularyRes> vocabulariesToAdminVocabulariesRes(
      List<Vocabulary> vocabularies) {
    return vocabularies.stream().map(this::vocabularyToAdminVocabularyResponse).toList();
  }
}
