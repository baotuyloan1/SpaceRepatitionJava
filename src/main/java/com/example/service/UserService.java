package com.example.service;

import com.example.dto.user.UserDto;
import com.example.entity.User;
import com.example.payload.request.SignupRequest;
import com.example.payload.response.MessageResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author BAO 7/3/2023
 */
public interface UserService {

    List<User> getAllUser();

    User signUpUser(SignupRequest user);

    boolean exitsByUserName(String username);

    boolean exitsByEmail(String email);

    ResponseEntity<MessageResponse> checkValidSignupRequest(SignupRequest signupRequest);

    User getInfoById(Long id);
}
