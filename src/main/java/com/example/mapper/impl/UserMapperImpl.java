package com.example.mapper.impl;

import com.example.dto.user.auth.UserSignUpResponse;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.springframework.stereotype.Component;

/**
 * @author BAO 7/19/2023
 */
@Component
public class UserMapperImpl implements UserMapper {
  @Override
  public UserSignUpResponse userToUserSignUpResponse(User user) {
    UserSignUpResponse userSignUpResponse = new UserSignUpResponse();
    userSignUpResponse.setId(user.getId());
    userSignUpResponse.setUsername(user.getUsername());
    userSignUpResponse.setEmail(user.getEmail());
    return userSignUpResponse;
  }
}
