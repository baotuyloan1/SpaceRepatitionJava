Áp dụng thuật toán SM-2
Link tham khảo: https://super-memory.com/english/ol/sm2.htm
Link FE online: http://116.105.222.85:8081/
SuperMemo2

- I(1):=1
- I(2):=6
- for n>2 I(n):=I(n-1)*EF

trong đó

- EF (E-Factor): hệ số độ dễ, phản ánh độ dễ hay khó của đối tượng trong việc ghi nhớ.
- I(n) (interval): trả về khoảng thời gian đối tượng sẽ lặp lại (tính bằng ngày) sau n lần thử

Ở đây em thay I(2) = 3 thay vì phải bằng 6
```java
learnEnglish.app.defaultEF=2.5
learnEnglish.app.defaultQ=5
learnEnglish.app.timeFirstDay=1
learnEnglish.app.timeSecondDay=3
learnEnglish.app.leastEF=1.3

// properties class
@Configuration
@PropertySource({"classpath:application-dev.properties"})
@Getter
@Setter
@Component
public class PropertiesConfig {
    @Value("${dir.resource.audioWord}")
    private String pathAudioWord;

    @Value("${dir.resource.audioSentence}")
    private String pathAudioSentence;

    @Value("${dir.resource.imgWord}")
    private String pathImgWord;

    @Value("${dir.resource.imgCourse}")
    private String pathImgCourse;

    @Value("${dir.resource.imgTopic}")
    private String pathImgTopic;

    @Value("${learnEnglish.app.defaultEF}")
    private float defaultEF;

    @Value("${learnEnglish.app.timeFirstDay}")
    private int defaultFirstDay;

    @Value("${learnEnglish.app.timeSecondDay}")
    private int defaultSecondDay;
    @Value("${learnEnglish.app.leastEF}")
    private float leastEF;

    @Value("${learnEnglish.app.defaultQ}")
    private short defaultQ;

}
```
Trước tiên lấy các từ chưa được học trong 1 topic
```java
  @Transactional
  @Override
  public List<UserLearnRes> getNextWordToReview() {
    Date currentDate = new Date();
    List<UserVocabulary> userVocabularyList;
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Date nearestDate = userVocabularyRepository.getNearestDate(userDetails.getId());
    if (currentDate.compareTo(nearestDate) >= 1) {
      userVocabularyList =
          userVocabularyRepository.getVocabulariesAfterCurrent(userDetails.getId(), new Date());
    } else {
      int oneHour = 60 * 60 * 1000;
      userVocabularyList =
          userVocabularyRepository.getVocabulariesAfterCurrent(
              userDetails.getId(), new Date(nearestDate.getTime() + oneHour));
    }
    List<Vocabulary> vocabularies = getVocabulariesFromUserVocabularies(userVocabularyList);
    return convertReviewVocabulariesToUserRes(vocabularies);
  }

private List<UserLearnRes> convertNewVocabulariesToUserRes(List<Vocabulary> vocabularies) {
        return vocabularies.stream()
        .map(
        vocabulary -> {
        Set<TypeLearnRes> typeLearnResList = new LinkedHashSet<>();
        addInfoType(typeLearnResList);
        addSelectType(typeLearnResList, vocabulary);
        addMeaningType(typeLearnResList);
        addListeningType(typeLearnResList);
        UserLearnRes userLearnRes = vocabularyMapper.vocabularyToUserLearnRes(vocabulary);
        userLearnRes.setLearnTypes(typeLearnResList);
        return userLearnRes;
        })
        .toList();
        }
```
Mẫu json đạt được
```json
[
  {
    "vocabularyId": 15,
    "word": "Anthropology",
    "meaningWord": "ngành nhân học",
    "type": "noun [ U ]",
    "ipa": "/ˌænθrəˈpɑːlədʒi/",
    "sentence": "Have you ever studied anthropology?",
    "meaningSentence": "Bạn đã từng học ngành nhân chủng học chưa?",
    "audioSentence": "15_sentence.mp3",
    "audioWord": "15_word.mp3",
    "img": "15_word.jpg",
    "learnTypes": [
      {
        "type": "info"
      },
      {
        "type": "select",
        "idQuestion": 15,
        "question": "<p>Nghĩa của từ <strong><u>Anthropology </u></strong>là gì?</p>",
        "idRightAnswer": 31,
        "answers": [
          {
            "id": 32,
            "answer": "Ngành khảo cổ học"
          },
          {
            "id": 33,
            "answer": "Ngành sinh học"
          },
          {
            "id": 31,
            "answer": "Ngành nhân học"
          },
          {
            "id": 34,
            "answer": "Tôi không biết"
          }
        ]
      },
      {
        "type": "mean"
      },
      {
        "type": "listen"
      }
    ]
  },
  {
    "vocabularyId": 16,
    "word": "architecture",
    "meaningWord": "Kiến trúc xây dựng",
    "type": "noun [ U ]",
    "ipa": "/ˈɑːr.kə.tek.tʃɚ/",
    "sentence": " The temple is noted for its architecture.",
    "meaningSentence": " Ngôi đền nổi tiếng vì kiến trúc của nó.",
    "audioSentence": "16_sentence.mp3",
    "audioWord": "16_word.mp3",
    "img": "16_word.png",
    "learnTypes": [
      {
        "type": "info"
      },
      {
        "type": "select",
        "idQuestion": 16,
        "question": "<p>Nghĩa của từ <strong><u>architecture</u></strong> là gì ?</p>",
        "idRightAnswer": 36,
        "answers": [
          {
            "id": 35,
            "answer": "Nhà thi đấu"
          },
          {
            "id": 38,
            "answer": "Đấu kiếm"
          },
          {
            "id": 37,
            "answer": "Con ong"
          },
          {
            "id": 36,
            "answer": "Kiến trúc"
          }
        ]
      },
      {
        "type": "mean"
      },
      {
        "type": "listen"
      }
    ]
  },
  ... còn nữa
]
```
Thêm từ vựng mới được học
```java
  public UserVocabulary createUserVocabulary(Vocabulary vocabulary, long userId) {
    UserVocabulary userVocabulary = new UserVocabulary();
    Date currentDate = new Date();
    userVocabulary.setId(new UserVocabularyId(userId, vocabulary.getId()));
    userVocabulary.setSubmitDate(currentDate);
    userVocabulary.setEf(env.getDefaultEF());
    userVocabulary.setQ(env.getDefaultQ());
    userVocabulary.setCountLearn(1);
    userVocabulary.setDayInterval(env.getDefaultFirstDay());
    userVocabulary.setReviewDate(calculateDateToReview(currentDate, env.getDefaultFirstDay()));
    return userVocabularyRepository.save(userVocabulary);
  }

```

Tiếp đến là lấy các từ vựng cần được ôn tập khi hạn ở quá khứ, hoặc các tự vựng gần nhất sắp được ôn tập
```java
  @Transactional
  @Override
  public List<UserLearnRes> getNextWordToReview() {
    Date currentDate = new Date();
    List<UserVocabulary> userVocabularyList;
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Date nearestDate = userVocabularyRepository.getNearestDate(userDetails.getId());
    if (currentDate.compareTo(nearestDate) >= 1) {
      userVocabularyList =
          userVocabularyRepository.getVocabulariesAfterCurrent(userDetails.getId(), new Date());
    } else {
      int oneHour = 60 * 60 * 1000;
      userVocabularyList =
          userVocabularyRepository.getVocabulariesAfterCurrent(
              userDetails.getId(), new Date(nearestDate.getTime() + oneHour));
    }
    List<Vocabulary> vocabularies = getVocabulariesFromUserVocabularies(userVocabularyList);
    return convertReviewVocabulariesToUserRes(vocabularies);
  }
```

Lưu từ vựng mới được học khi type select
```java
  public UserVocabulary saveLearnNewWord(Vocabulary vocabulary,boolean isLearnAgain,long userId){
        UserVocabulary userVocabulary=new UserVocabulary();
        Date currentDate=new Date();
        userVocabulary.setId(new UserVocabularyId(userId,vocabulary.getId()));
        userVocabulary.setSubmitDate(currentDate);
        userVocabulary.setEf(env.getDefaultEF());
        userVocabulary.setQ(env.getDefaultQ());
        if(isLearnAgain){
        int defaultCountLearn=0;
        updateQAndEFInFalseAnswer(userVocabulary);
        userVocabulary.setReviewDate(currentDate);
        userVocabulary.setCountLearn(defaultCountLearn);
        }else{
        userVocabulary.setCountLearn(1);
        userVocabulary.setDayInterval(env.getDefaultFirstDay());
        userVocabulary.setReviewDate(calculateDateToReview(currentDate,env.getDefaultFirstDay()));
        }
        return userVocabularyRepository.save(userVocabulary);
        }
```

Update lại hệ số Q và EF khi trả lời đúng hoặc sai
```java

private void updateQAndEFInRightAnswer(UserVocabulary userVocabulary) {
    short currentQ = userVocabulary.getQ();
    int maxQ = 5;
    if (currentQ < maxQ) {
      userVocabulary.setQ(++currentQ);
    } else {
      userVocabulary.setQ(currentQ);
    }
    float currentEF = calculateCurrentEF(userVocabulary.getEf(), currentQ);
    userVocabulary.setEf(currentEF);
  }

  private void updateQAndEFInFalseAnswer(UserVocabulary userVocabulary) {
    short currentQ = userVocabulary.getQ();
    int minQ = 0;
    if (currentQ < minQ) {
      userVocabulary.setQ(currentQ);
    } else {
      userVocabulary.setQ(--currentQ);
    }
    float currentEF = calculateCurrentEF(userVocabulary.getEf(), currentQ);
    userVocabulary.setEf(currentEF);
  }

```
Hàm tính toán EF
```java
  public float calculateCurrentEF(float currentEF, short q) {
    float ef = (float) (currentEF + (0.1 - (5 - q) * (0.08 + (5 - q) * 0.02)));
    if (ef < env.getLeastEF()) ef = env.getLeastEF();
    return ef;
  }
```
Tính toán lại ngày cần ôn tập, trả về ngày ôn tập tiếp theo
```java
  private Date calculateDateToReview(Date currentDate, long dayInterval) {
    long endDateMillis = currentDate.getTime() + TimeUnit.DAYS.toMillis(dayInterval);
    return new Date(endDateMillis);
  }
```

