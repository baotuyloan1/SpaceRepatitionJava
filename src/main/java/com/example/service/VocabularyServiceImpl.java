package com.example.service;

import com.example.entity.Vocabulary;
import com.example.exception.CustomerException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.VocabularyRepository;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author BAO 6/29/2023
 */
@Service
public class VocabularyServiceImpl implements VocabularyService {

  private final VocabularyRepository vocabularyRepository;

  @Value("${dir.resource.audioWord}")
  private String dirAudioWord;

  @Value("${dir.resource.audioSentence}")
  private String dirAudioSentence;

  @Value("${dir.resource.imgWord}")
  private String dirImgWord;

  public VocabularyServiceImpl(VocabularyRepository vocabularyRepository) {
    this.vocabularyRepository = vocabularyRepository;
  }

  @Transactional
  @Override
  public Vocabulary createWord(
      Vocabulary vocabulary,
      MultipartFile audioWord,
      MultipartFile audioSentence,
      MultipartFile img) {
    try {
      vocabulary.setAddedDate(new Date());
      Vocabulary savedWord = vocabularyRepository.save(vocabulary);

      // saveAudioWord, có nhiều extentions file mp3 khác nhau
      String audioWordFileName = saveFile(audioWord, savedWord.getId(), dirAudioWord, "word");
      savedWord.setAudioWord(audioWordFileName);

      // saveAudioSentence
      String audioSentenceFileName =
          saveFile(audioSentence, savedWord.getId(), dirAudioSentence, "sentence");
      savedWord.setAudioSentence(audioSentenceFileName);

      // saveImage
      String imageFileName = saveFile(img, savedWord.getId(), dirImgWord, "word");
      savedWord.setImg(imageFileName);

      return vocabularyRepository.save(savedWord);
    } catch (IOException e) {
      throw new CustomerException(e, "Something went wrong");
    }
  }

  private String saveFile(MultipartFile audio, long id, String path, String audioType)
      throws IOException {
    String extension = FilenameUtils.getExtension(audio.getOriginalFilename());
    StringBuilder absolutePath = new StringBuilder();
    StringBuilder fileName = new StringBuilder();
    fileName.append(id).append("_").append(audioType).append(".").append(extension);
    absolutePath.append(path).append(File.separator).append(fileName);
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
    try {
      Files.copy(audio.getInputStream(), Paths.get(absolutePath.toString()));
    } catch (IOException e) {
      throw new IOException(e);
    }
    return fileName.toString();
  }

  @Override
  public Vocabulary getById(Long id) {
    return vocabularyRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(false, "Audio not found"));
  }

  @Transactional
  @Override
  public Vocabulary update(Vocabulary vocabulary, MultipartFile audio) {
    return vocabularyRepository.save(vocabulary);
  }

  @Override
  public void deleteAudio(Long id) {}

  @Override
  public List<Vocabulary> getAllVocabulary() {
    return vocabularyRepository.findAll();
  }

  @Override
  public List<Vocabulary> getVocabularyNotLearnedByUserId(long id) {
    return vocabularyRepository.findAllNotLearnedBy(id);
  }
}
