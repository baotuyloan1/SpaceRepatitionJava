package com.example.repository;

import com.example.dto.user.CustomUserVocabulariesResult;
import com.example.entity.UserVocabulary;
import com.example.entity.UserVocabularyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author BAO 7/3/2023
 */
@Repository
public interface UserVocabularyRepository extends JpaRepository<UserVocabulary, UserVocabularyId> {

//  @Query(
//      "SELECT uv FROM UserVocabulary uv WHERE (uv.endDate < ?2 ) OR uv.endDate BETWEEN (SELECT MIN(uv.endDate)FROM uv) AND ((SELECT MIN(uv.endDate) FROM uv ) + 2*60 * 60) AND uv.id.userId = ?1 ")
//  List<UserVocabulary> getUserVocabularyNearest(long userid, Date currentDate);

  @Query("SELECT MIN(uv.reviewDate) FROM UserVocabulary  uv WHERE  uv.user.id = ?1")
  Date getNearestDate(long userid);

  @Query("SELECT uv FROM UserVocabulary uv  WHERE uv.user.id = ?1 AND  uv.reviewDate < ?2 ORDER BY uv.reviewDate ASC")
  List<UserVocabulary> getVocabulariesAfterCurrent(Long id, Date date);

//  @Query("SELECT count(user.id), user FROM UserVocabulary  uv JOIN uv.user user WHERE  uv.reviewDate < ?1 GROUP BY user")
//  List<Object[]> getUserVocabulariesBeforeCurrent(Date currentDate);

  @Query("SELECT NEW com.example.dto.user.CustomUserVocabulariesResult(count (uv.user.id),uv.user) FROM UserVocabulary  uv JOIN uv.user user WHERE  uv.reviewDate < ?1 GROUP BY user")
  List<CustomUserVocabulariesResult> getUserVocabulariesBeforeCurrent(Date currentDate);
  @Query("SELECT uv from UserVocabulary  uv WHERE  uv.reviewDate < ?1 AND uv.reviewDate> ?2")
  List<UserVocabulary> getUserVocabulariesPrepare(Date futureDate, Date curentDate);
}
