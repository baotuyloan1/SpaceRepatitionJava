Các khai niệm liên quan

- Service Worker: là 1 loại wroker trong trình duyê có khả năng chạy ngầm và thực hiện các tác vụ nền. Service Worker là thành phần quan trọng trong việc thực hiện Web Push Notifications. Nó có khả năng nhận các sự kiện push từ server và hiển thị thông báo cho người dùng ngay cả khi web không được mở
- Push Server: là 1 loại mày chủ server chịu trách nhiệm gửi các thông báo push đến trình duyệt cho người dùng. Push service sẽ liên lạc với trình duyệt thông qua các API để gửi thông báo push

Giả sử ban muốn triển khai Web Push Notifications trong ứng dụng web của mình. Để gửi thông báo push đến trình duyệt người dùng. Push Server cần xác thực bản thân mình với trình duyệt để đảm bảo tính toàn vẹn và đáng tin cậy của thông báo được gửi đi. Đây là lúc VAPID(Voluntary Application Server Identification) key xuất hiện
**VAPID key** là 1 cặp khóa công khai và riêng tư dùng để xác thục Push Server.

(BouncyCastleProvider) là triển khai của Java Cryptography Architecture (JCA) và ava Cryptography Extension (JCE). Thư viện này cung cấp các cài đặt cho các thuật toán mã hóa, chữ ký điện tử, bảo mật và hỗ trợ nhiều chuẩn mã hóa với chữ ký khác nhau
Sử dụng nó đễ hỗ trợ cho mã hóa VAPID key
```java
 @PostConstruct
  private void init() throws GeneralSecurityException {
    Security.addProvider(new BouncyCastleProvider());
    pushService = new PushService(env.getVapidPublicKey(), env.getVapidPrivateKey());
  }
```

**VAPID Key** là 1 cặp khóa (public-private key) dùng để xác thục Push Server. Khi triển khai Web Push, cần cung cấp VAPID key cho Push Server để chúng được gửi kèm với các yêu cầu gửi thông báo push tới trình duyệt. Trình duyệt sau đó sẽ sử dụng VAPID key để xác minh tính toàn vẹn của thông báo và đảm bảo rằng thông báo đến từ máy chủ được xác thực

VAPID key cũng đảm bảo tính riêng tư vì chỉ Push Server mới biết khóa riêng (private key), trong khi trình duyệt chỉ cần biết khóa công khai (public key) để xác minh chữ ký của thông báo.