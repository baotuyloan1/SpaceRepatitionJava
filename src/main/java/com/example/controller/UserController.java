package com.example.controller;

import com.example.dto.user.UserDto;
import com.example.entity.User;
import com.example.service.UserService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author BAO 7/3/2023
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping({"", "/"})
  public ResponseEntity<List<User>> getAll() {
    return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
  }

  @PostMapping({"/singup"})
  public ResponseEntity<User> singUpUser (@RequestBody UserDto userDto){
    userService.signUpUser( userDto);
    return null;
  }

  @PostMapping("/singin")
  public ResponseEntity<User> singInUser (@RequestBody UserDto userDto){

  }

  @PostMapping("/signout")

}
