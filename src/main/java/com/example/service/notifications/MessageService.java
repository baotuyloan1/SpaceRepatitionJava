package com.example.service.notifications;

import com.example.config.PropertiesConfig;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.annotation.PostConstruct;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author ADMIN 7/28/2023
 */
@Service
public class MessageService {

  private PropertiesConfig env;

  private PushService pushService;
  private List<Subscription> subscriptions;

  @PostConstruct
  private void init() throws GeneralSecurityException {
    Security.addProvider(new BouncyCastleProvider());
    pushService = new PushService(env.getPublicKeyNotification(), env.getPrivateKeyNotification());
  }

  public void subscribe(Subscription subscription) {
    System.out.println("Subscribed to" + subscription.endpoint);
    this.subscriptions.add(subscription);
  }

  public void unsubscribe(String endpoint) {
    System.out.println("Unsubscribed from " + endpoint);
    subscriptions = subscriptions.stream().filter(s -> endpoint.equals(s.endpoint)).toList();
  }

  public void sendNotification(Subscription subscription, String messageJson) {
    try {
      pushService.send(new Notification(subscription, messageJson));
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    } catch (JoseException e) {
      throw new RuntimeException(e.getMessage());
    } catch (ExecutionException e) {
      throw new RuntimeException(e.getMessage());
    } catch (InterruptedException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Scheduled(fixedRate = 15000)
  private void sendNotifications() {
    System.out.println("Sending notifications to all subscribes");

    var json = """
{
"title": "Server says hello!,
"body": "it is now :%s"
}
""";

    subscriptions.forEach(
        subscription -> sendNotification(subscription, String.format(json, LocalTime.now())));
  }

  public String getPublicKey() {
    return env.getPublicKeyNotification();
  }
}
