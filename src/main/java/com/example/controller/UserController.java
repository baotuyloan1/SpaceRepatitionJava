package com.example.controller;

import com.example.payload.request.LoginRequest;
import com.example.payload.request.SignupRequest;
import com.example.payload.response.MessageResponse;
import com.example.payload.response.UserInfoResponse;
import com.example.security.jwt.JwtUtils;
import com.example.security.services.UserDetailsImpl;
import com.example.service.UserService;
import com.example.service.UserVocabularyService;
import com.example.service.VocabularyService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author BAO 7/3/2023
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final AuthenticationManager authenticationManager;

  private final VocabularyService vocabularyService;

  private final UserVocabularyService userVocabularyService;
  private final JwtUtils jwtUtils;

  public UserController(
          UserService userService, AuthenticationManager authenticationManager, VocabularyService vocabularyService, UserVocabularyService userVocabularyService, JwtUtils jwtUtils) {
    this.userService = userService;
    this.authenticationManager = authenticationManager;
    this.vocabularyService = vocabularyService;
    this.userVocabularyService = userVocabularyService;
    this.jwtUtils = jwtUtils;
  }



  @PostMapping({"/signup"})
  public ResponseEntity<?> singUpUser(@RequestBody SignupRequest signupRequest) {
    if (userService.exitsByUserName(signupRequest.getUsername())) {
      return ResponseEntity.badRequest()
          .body(
              new MessageResponse(
                  HttpStatus.BAD_REQUEST.value(), "Error: Username is already taken!!"));
    }
    if (userService.exitsByEmail(signupRequest.getEmail())) {
      return ResponseEntity.badRequest()
          .body(
              new MessageResponse(
                  HttpServletResponse.SC_BAD_REQUEST, "Error: Email is already in use!"));
    }
    userService.signUpUser(signupRequest);

    return ResponseEntity.ok(
        new MessageResponse(HttpServletResponse.SC_CREATED, "User registered successfully"));
  }

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
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
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

  @GetMapping("/learn/{topicId}")
  public List<Map<String, Object>> listVocabulary(@PathVariable("topicId") int topicId) {
    return vocabularyService.getLearnVocabulary(topicId);
  }

  @PostMapping("/learn/newVocabulary")
  public ResponseEntity<?> saveLearnedVocabulary(@RequestBody long idVocabulary){
    userVocabularyService.saveNewLearnedVocabulary(idVocabulary);
    return new ResponseEntity<>("Saved learned word",  HttpStatus.OK);
  }


}
