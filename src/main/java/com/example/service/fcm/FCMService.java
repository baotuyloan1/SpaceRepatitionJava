package com.example.service.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author ADMIN 7/27/2023
 */
@Service
public class FCMService {

//  @PostConstruct
//  private void getAccessToken() throws IOException {
//    FirebaseOptions options =
//        FirebaseOptions.builder().setCredentials(GoogleCredentials.getApplicationDefault()).build();
//    FirebaseApp.initializeApp(options);
//  }

  /**
   * second: Giây trong phút (0-59) minute: Phút trong giờ (0-59) hour: Giờ trong ngày (0-23)
   * dayOfMonth: Ngày trong tháng (1-31) month: Tháng trong năm (1-12) dayOfWeek: Ngày trong tuần
   * (0-7), 0 và 7 đại diện cho Chủ Nhật, 1 đến 6 đại diện cho Thứ 2 đến Thứ 7.
   */
  // 0 0 7,19 * * *
//  @Scheduled(cron = "* * * * * *")
//  public void sendNotificationAt7AM() {
//    String registrationToken =
//        "cOi8H25LjFPnmjoqj2gOVh:APA91bHkFGJeLN7r611RAbGPQFcblZu_GtdUwRSRIOg6QzW08_4SBXjsl6d720Nkfz8Z2ILqr-Yv3U-bWoLH-3VjniS9QIwfkvGgN3SjC3UFn31NguueNEcERJPF2pBcV19x-_KeKtKE";
//    Message message =
//        Message.builder()
//            .setNotification(
//                Notification.builder().setTitle("Title nè").setBody("Làm gì đó").build())
//            .putData("socre", "850")
//            .putData("time", "2:45")
//            .setToken(registrationToken)
//            .build();
//    try {
//      String response = FirebaseMessaging.getInstance().send(message);
//      System.out.println("Successfully went message: " + response);
//
//    } catch (FirebaseMessagingException e) {
//      throw new RuntimeException(e);
//    }
//  }
}
