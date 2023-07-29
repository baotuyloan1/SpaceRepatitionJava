package com.example.mapper;

import com.example.dto.auth.UserMobileSignInRes;
import com.example.dto.auth.UserSignUpResponse;
import com.example.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author BAO 7/11/2023
 */

@Component
public interface UserMapper {

    UserSignUpResponse  userToUserSignUpResponse(User user);

    UserMobileSignInRes userToUserMobileSignInRes(User user);


}
