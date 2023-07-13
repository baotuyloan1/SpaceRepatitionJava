package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author BAO 7/13/2023
 */
@RestController
@RequestMapping("/api/management")
public class ManageController {
    private final UserService userService;

    public ManageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

}
