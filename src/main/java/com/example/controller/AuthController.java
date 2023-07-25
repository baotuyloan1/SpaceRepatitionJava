package com.example.controller;

import com.example.dto.user.auth.MobileSignInRes;
import com.example.dto.user.auth.UserSignInResponse;
import com.example.dto.user.auth.UserSignUpRequest;
import com.example.dto.user.auth.UserSignUpResponse;
import com.example.exception.ApiRequestException;
import com.example.exception.ErrorResponse;
import com.example.payload.request.LoginRequest;
import com.example.payload.response.MessageResponse;
import com.example.security.services.UserDetailsImpl;
import com.example.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
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

  private ResponseEntity<?> badRequest(String message) {
    return ResponseEntity.badRequest()
        .body(
            new ErrorResponse(
                "error",
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(),
                message));
  }

  @PostMapping("/sign-up")
  public ResponseEntity<Long> signUpUser(@RequestBody UserSignUpRequest signUpRequest) {
    boolean isExistsUserName = authService.isExistUserName(signUpRequest.getUsername());
    boolean isExistsEmail = authService.isExistEmail(signUpRequest.getEmail());
    if (isExistsEmail && isExistsUserName) {
      throw new ApiRequestException("Email và UserName đã tồn tại trong hệ thống");
    }
    if (isExistsEmail) {
      throw new ApiRequestException("Email đã tồn tại trong hệ thống");
    }
    if (isExistsUserName) {
      throw new ApiRequestException("UserName đã tồn tại trong hệ thống");
    }
    UserSignUpResponse user = authService.signUpUser(signUpRequest);
    return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
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
  public ResponseEntity<MobileSignInRes> signInMobile(@RequestBody LoginRequest loginRequest) {
    MobileSignInRes res = authService.mobileSignIn(loginRequest);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @PostMapping("/sign-out")
  public ResponseEntity<MessageResponse> logoutUser() {
    ResponseCookie cookie = authService.getCleanJwtCookie();
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse(HttpServletResponse.SC_OK, "You've been signed out!"));
  }
}
