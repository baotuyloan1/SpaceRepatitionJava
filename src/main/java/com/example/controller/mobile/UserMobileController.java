package com.example.controller.mobile;

import com.example.dto.mobile.UserResponseMobile;
import com.example.payload.request.LoginRequest;
import com.example.security.jwt.JwtUtils;
import com.example.security.services.UserDetailsImpl;
import com.example.service.AuthService;
import com.example.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author BAO 7/14/2023
 */

@RestController
@RequestMapping("/api/mobile/user")
public class UserMobileController {

    private final AuthService authService;
    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    public UserMobileController(AuthService authService, UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authService = authService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> loginUser (@RequestBody UserSignUpRequest userSignupRequest){
//        ResponseEntity<MessageResponse> BAD_REQUEST = AuthService.isValidSignUpRequest(userSignupRequest);
//        if (BAD_REQUEST != null) return BAD_REQUEST;
//
//        authService.signUpUser(userSignupRequest);
//        return ResponseEntity.ok(
//                new MessageResponse(HttpServletResponse.SC_CREATED, "User registered successfully"));
//    }



    @PostMapping("/signin")
    public ResponseEntity<UserResponseMobile> singInUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateJwtToken(userDetails);
        List<String> roles =
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
        UserResponseMobile userResponseMobile = new UserResponseMobile();
    userResponseMobile.setId(userDetails.getId());
    userResponseMobile.setUsername(userDetails.getUsername());
    userResponseMobile.setEmail(userDetails.getEmail());
    userResponseMobile.setRoles(roles);
    userResponseMobile.setJwtToken(jwtToken);
    return ResponseEntity.ok()
        .header(HttpHeaders.ACCEPT, jwtToken)
        .body(
           userResponseMobile);
    }

    @GetMapping("/getInfo")
    public ResponseEntity<?> getInfo(){
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       return ResponseEntity.ok(userService.getUserById(userDetails.getId()));
    }


}
