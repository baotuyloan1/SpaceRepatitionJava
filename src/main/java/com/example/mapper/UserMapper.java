package com.example.mapper;

import com.example.dto.SignUpRequest;
import com.example.dto.auth.UserMobileSignInRes;
import com.example.dto.auth.UserSignUpRequest;
import com.example.dto.auth.UserSignUpResponse;
import com.example.entity.Role;
import com.example.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author BAO 7/11/2023
 */

@Component
public interface UserMapper {

    UserSignUpResponse  userToUserSignUpResponse(User user);

    UserMobileSignInRes userToUserMobileSignInRes(User user);

    User userSignUpRequestToUser(SignUpRequest req, List<Role> roles);


}
