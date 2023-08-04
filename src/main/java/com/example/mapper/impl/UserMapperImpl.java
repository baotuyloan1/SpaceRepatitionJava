package com.example.mapper.impl;

import com.example.dto.SignUpRequest;
import com.example.dto.auth.UserMobileSignInRes;
import com.example.dto.auth.UserSignUpResponse;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author BAO 7/19/2023
 */
@Component
@AllArgsConstructor
public class UserMapperImpl implements UserMapper {
  private PasswordEncoder passwordEncoder;

  @Override
  public UserSignUpResponse userToUserSignUpResponse(User user) {
    UserSignUpResponse userSignUpResponse = new UserSignUpResponse();
    userSignUpResponse.setId(user.getId());
    userSignUpResponse.setUsername(user.getUsername());
    userSignUpResponse.setEmail(user.getEmail());
    return userSignUpResponse;
  }

  @Override
  public UserMobileSignInRes userToUserMobileSignInRes(User user) {
    UserMobileSignInRes res = new UserMobileSignInRes();
    res.setPhone(user.getPhone());
    res.setId(user.getId());
    res.setEmail(user.getEmail());
    res.setUsername(user.getUsername());
    res.setCountWords(user.getCountWords());
    res.setFirstName(user.getFirstName());
    res.setLastName(user.getLastName());
    res.setPhone(user.getPhone());
    return res;
  }

  @Override
  public User userSignUpRequestToUser(SignUpRequest req, List<Role> roles) {
    User user = new User();
    user.setUsername(req.getUsername());
    user.setPassword(passwordEncoder.encode(req.getPassword()));
    user.setRoles(roles);
    user.setFirstName(req.getFirstName());
    user.setLastName(req.getLastname());
    user.setCountWords(0);
    user.setEmail(req.getEmail());
    user.setPhone(req.getPhone());
    return user;
  }
}
