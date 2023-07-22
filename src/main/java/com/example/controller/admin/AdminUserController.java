package com.example.controller.admin;

import com.example.entity.User;
import com.example.service.UserService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class AdminUserController {
    private final UserService userService;


    @GetMapping({"", "/"})
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

}
