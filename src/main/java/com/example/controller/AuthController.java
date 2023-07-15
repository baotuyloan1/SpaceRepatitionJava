package com.example.controller;

import com.example.payload.request.LoginRequest;
import com.example.payload.request.SignupRequest;
import com.example.payload.response.MessageResponse;
import com.example.payload.response.UserInfoResponse;
import com.example.security.jwt.JwtUtils;
import com.example.security.services.UserDetailsImpl;
import com.example.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author BAO 7/15/2023
 */
@RestController
@RequestMapping("/api/auth/")
public class AuthController {

  private final UserService userService;

  private final AuthenticationManager authenticationManager;

  private final JwtUtils jwtUtils;

  public AuthController(
      UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
    this.userService = userService;
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
  }

  @PostMapping({"/signup"})
  public ResponseEntity<?> singUpUser(@RequestBody SignupRequest signupRequest) {
    ResponseEntity<MessageResponse> BAD_REQUEST =
        userService.checkValidSignupRequest(signupRequest);
    if (BAD_REQUEST != null) return BAD_REQUEST;
    userService.signUpUser(signupRequest);
    return ResponseEntity.ok(
        new MessageResponse(HttpServletResponse.SC_CREATED, "User registered successfully"));
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
   * Có 2 cách 1 là như này, còn 2 có thể thêm vào header
   *
   * @param loginRequest
   * @return
   */
//  @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
  @PostMapping("/signin")
  public ResponseEntity<UserInfoResponse> singInUser(@RequestBody LoginRequest loginRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
    List<String> roles =
        userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    HttpHeaders headers = new HttpHeaders();
    /**
     * THong bao cho trình duyệt biết máy chủ đồng ý chia sẻ cookie và thông tin xác thực
     * do cấu hiình bên MvcConfig rồi nên không cần cấu hình lại
     */
//    headers.add("Access-Control-Allow-Credentials","true");
//    headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
    headers.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());
    return ResponseEntity.ok()
        .headers(headers)
        .body(
            new UserInfoResponse(
                userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
  }

  @PostMapping("/sigout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getClearnJwtCookie();
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse(HttpServletResponse.SC_OK, "You've been signed out!"));
  }
}
