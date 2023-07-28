package com.example.service.fcm;

import com.example.dto.fcm.PushNotificationRequest;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.Message;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * @author ADMIN 7/27/2023
 */
@Service
public class FCMService {

//  public void sendMessage(Map<String, String> data, PushNotificationRequest request) {
//    Message message = getPreConfiguredMessageWithData(data, request);
//  }

  private static String getAccessToken() throws IOException {
    String SCOPES = "https://www.googleapis.com/auth/firebase.messaging";
    GoogleCredentials googleCredentials =
        GoogleCredentials.fromStream(new FileInputStream("fcm/firebase-service-account.json"))
            .createScoped(Arrays.asList(SCOPES));
    googleCredentials.refreshAccessToken();
    return googleCredentials.getAccessToken().getTokenValue();
  }

  //    private Message getPreConfiguredMessageWithData(Map<String, String> data,
  // PushNotificationRequest request) {
  //    }
  //
  //    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request){
  //    }

//    private HttpURLConnection test(){
//        URL url = new URL(BASE_URL + FCM_SEND_ENDPOINT);
//
//        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//        httpURLConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
//        httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");
//        return httpURLConnection;
//    }
}
