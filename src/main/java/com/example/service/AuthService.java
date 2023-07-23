package com.example.service;

import com.example.dto.SignUpRequest;
import com.example.dto.user.auth.MobileSignInRes;
import com.example.dto.user.auth.UserSignInResponse;
import com.example.dto.user.auth.UserSignUpResponse;
import com.example.payload.request.LoginRequest;
import com.example.security.services.UserDetailsImpl;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;

/**
 * @author BAO 7/19/2023
 */
public interface AuthService {
    boolean isValidSignUpRequest(SignUpRequest userSignupRequest);

    boolean isExistUserName(String userName);

    boolean isExistEmail(String email);

    UserSignUpResponse signUpUser(SignUpRequest signUpRequest);

    UserSignInResponse getInfoUserSignIn(UserDetailsImpl loginRequest);

    ResponseCookie generateResponseCookie(UserDetailsImpl loginRequest);

    UserDetailsImpl login(Authentication authentication);

    ResponseCookie getCleanJwtCookie();

    String generateJwtStr(UserDetailsImpl userDetails);

    MobileSignInRes mobileSignIn(LoginRequest loginRequest);
}
