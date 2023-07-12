package com.example.service.impl;

import com.example.dto.user.UserDto;
import com.example.entity.User;
import com.example.enums.RoleUser;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author BAO 7/3/2023
 */
@Service
public class UserServiceImpl  implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Transactional(dontRollbackOn = Exception.class)
    @Override
    public User signUpUser(UserDto userDto) {
        userDto.setPassword(b);
        User user = new User(userDto);

    }
}
