package com.example.controller;

import com.example.dto.BaseResApi;
import com.example.dto.auth.UserMobileSignInRes;
import com.example.dto.auth.UserSignInResponse;
import com.example.dto.auth.UserSignUpRequest;
import com.example.dto.auth.UserSignUpResponse;
import com.example.exception.DataConflictException;
import com.example.exception.FieldsExistRes;
import com.example.payload.request.LoginRequest;
import com.example.payload.response.MessageResponse;
import com.example.security.services.UserDetailsImpl;
import com.example.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author BAO 7/15/2023
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  private final AuthenticationManager authenticationManager;

  public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
    this.authService = authService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<BaseResApi<UserSignUpResponse> > signUpUser(@RequestBody UserSignUpRequest signUpRequest) {
    boolean isExistsUserName = authService.isExistUserName(signUpRequest.getUsername());
    boolean isExistsEmail = authService.isExistEmail(signUpRequest.getEmail());
    List<FieldsExistRes> fields = new LinkedList<>();
    if (isExistsEmail) {
      FieldsExistRes field = new FieldsExistRes("email", "Email đã tồn tại trong hệ thống");
      fields.add(field);
    }
    if (isExistsUserName) {
      FieldsExistRes field = new FieldsExistRes("username", "Username đã tồn tại trong hệ thống");
      fields.add(field);
    }
    if (!fields.isEmpty())
      throw new DataConflictException("Dữ liệu đã tồn tại trong hệ thống", fields);

    UserSignUpResponse user = authService.signUpUser(signUpRequest);
    BaseResApi<UserSignUpResponse> baseResApi = new BaseResApi<>();
    baseResApi.setMessage("Đăng ký thành công");
    baseResApi.setData(user);
    return new ResponseEntity<>(baseResApi, HttpStatus.CREATED);
  }

  /**
   * Khi muốn chia sẽ cookie từ BE đến FE khác origin thì phải dùng allowCredentials= true Mặc định
   * chính sách chia sẽ gốc (CORS) không cho phép chia sẻ cookie và thông tin giữa các nguồn khác
   * nhau bất kể nguồn đó có nằm trong origins hay không.
   *
   * <p>Vì vậy để chia sẻ cookie và thông tin xác thực cần phải đặt allowCredentials = true
   * trong @CrossOrigin cùng với origins. Điều này báo hiệu cho trình duyệt rằng máy chủ đồng ý chia
   * sẻ cookie và thông tin xác thục cho các yêu cầu từ nguồn được chỉ định trong origins;
   *
   * <p>Có 2 cách 1 là như này, còn 2 có thể thêm vào header
   *
   * @param loginRequest
   * @return
   */
  @PostMapping("/sign-in")
  public ResponseEntity<UserSignInResponse> signInUser(@RequestBody LoginRequest loginRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
    UserDetailsImpl userDetails = authService.login(authentication);
    ResponseCookie cookieJwt = authService.generateResponseCookie(userDetails);
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.SET_COOKIE, cookieJwt.toString());
    return ResponseEntity.ok().headers(headers).body(authService.getInfoUserSignIn(userDetails));
  }

  @PostMapping("/mobile/sign-in")
  public ResponseEntity<BaseResApi<UserMobileSignInRes>> signInMobile(
      @RequestBody LoginRequest loginRequest) {
    UserMobileSignInRes res = authService.mobileSignIn(loginRequest);
    BaseResApi<UserMobileSignInRes> baseResApi = new BaseResApi<>();
    baseResApi.setMessage("Đăng nhập thành công");
    baseResApi.setData(res);
    return new ResponseEntity<>(baseResApi, HttpStatus.OK);
  }

  @PostMapping("/sign-out")
  public ResponseEntity<MessageResponse> logoutUser() {
    ResponseCookie cookie = authService.getCleanJwtCookie();
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse(HttpServletResponse.SC_OK, "You've been signed out!"));
  }
}
