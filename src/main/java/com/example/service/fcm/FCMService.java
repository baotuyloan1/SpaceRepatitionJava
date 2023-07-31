package com.example.service.fcm;

import com.example.dto.user.CustomUserVocabulariesResult;
import com.example.entity.Device;
import com.example.repository.DeviceRepository;
import com.example.repository.UserVocabularyRepository;
import com.example.service.UserVocabularyService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author ADMIN 7/27/2023
 */
@Service
@AllArgsConstructor
public class FCMService {

  private final UserVocabularyRepository userVocabularyRepository;
  private final UserVocabularyService userVocabularyService;
  private final DeviceRepository deviceRepository;

  @PostConstruct
  private void getAccessToken() throws IOException {
    if (FirebaseApp.getApps().isEmpty()) {
      FirebaseOptions options =
          FirebaseOptions.builder()
              .setCredentials(GoogleCredentials.getApplicationDefault())
              .build();
      FirebaseApp.initializeApp(options);
    }
  }

  /**
   * second: Giây trong phút (0-59) minute: Phút trong giờ (0-59) hour: Giờ trong ngày (0-23)
   * dayOfMonth: Ngày trong tháng (1-31) month: Tháng trong năm (1-12) dayOfWeek: Ngày trong tuần
   * (0-7), 0 và 7 đại diện cho Chủ Nhật, 1 đến 6 đại diện cho Thứ 2 đến Thứ 7.
   */
  //   0 0 7,19 * * *
  //  @Scheduled(cron = "* * * * * *")
  @Transactional
  public void sendNotificationAt7AM() {
    Date currentDate = new Date();
    List<CustomUserVocabulariesResult> userVocabularies =
        userVocabularyRepository.getUserVocabulariesBeforeCurrent(currentDate);
    for (CustomUserVocabulariesResult item : userVocabularies) {
      for (Device deviceUser : item.getUser().getDevices()) {
        String registrationToken = deviceUser.getDeviceToken();
        Message message =
            Message.builder()
                .setNotification(
                    Notification.builder()
                        .setTitle("Vào ôn tập ngay thôi nào")
                        .setBody("Đang có " + item.getCount() + " từ cần được ôn tập")
                        .build())
                .setToken(registrationToken)
                .build();
        try {
          String response = FirebaseMessaging.getInstance().send(message);
          System.out.println("Successfully went message: " + response);
        } catch (FirebaseMessagingException e) {
          if (e.getMessagingErrorCode().name().equals("UNREGISTERED")) {
            deviceRepository.delete(deviceUser);
          }
        }
      }
    }
  }
}
