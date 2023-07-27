package com.example.mapper;

import com.example.dto.auth.UserSignUpResponse;
import com.example.dto.user.UserLearnRes;
import com.example.entity.User;
import com.example.entity.Vocabulary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author BAO 7/11/2023
 */

@Component
public interface UserMapper {

    UserSignUpResponse  userToUserSignUpResponse(User user);



}
