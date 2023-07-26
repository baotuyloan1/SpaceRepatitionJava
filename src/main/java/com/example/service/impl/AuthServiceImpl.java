package com.example.service.impl;

import com.example.dto.SignUpRequest;
import com.example.dto.auth.MobileSignInRes;
import com.example.dto.auth.UserSignInResponse;
import com.example.dto.auth.UserSignUpResponse;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.enums.RoleUser;
import com.example.mapper.UserMapper;
import com.example.payload.request.LoginRequest;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.security.jwt.JwtUtils;
import com.example.security.services.UserDetailsImpl;
import com.example.service.AuthService;
import jakarta.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author BAO 7/19/2023
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;
  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;

  @Override
  public boolean isValidSignUpRequest(SignUpRequest userSignupRequest) {
    return false;
  }

  @Override
  public boolean isExistUserName(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public boolean isExistEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Transactional(rollbackOn = Exception.class)
  @Override
  public UserSignUpResponse signUpUser(SignUpRequest signUpRequest) {
    Set<RoleUser> enumRoles = signUpRequest.getRoles();
    List<Role> roles = new LinkedList<>();
    if (enumRoles == null || enumRoles.isEmpty()) {
      Role userRole =
          roleRepository
              .findByName(RoleUser.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
      roles.add(userRole);
    } else {
      enumRoles.forEach(
          roleUser -> {
            Role role =
                roleRepository
                    .findByName(roleUser)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(role);
          });
    }
    User user =
        new User(
            signUpRequest.getId(),
            signUpRequest.getUsername(),
            roles,
            signUpRequest.getFirstName(),
            signUpRequest.getLastname(),
            0,
            passwordEncoder.encode(signUpRequest.getPassword()),
            signUpRequest.getEmail(),
            signUpRequest.getPhone());
    User savedUser = userRepository.save(user);
    return userMapper.userToUserSignUpResponse(savedUser);
  }

  @Override
  public UserSignInResponse getInfoUserSignIn(UserDetailsImpl userDetails) {
    List<String> roles =
        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    return new UserSignInResponse(
        userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
  }

  public UserDetailsImpl login(Authentication authentication) {
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return (UserDetailsImpl) authentication.getPrincipal();
  }

  @Override
  public ResponseCookie getCleanJwtCookie() {
    return jwtUtils.getClearnJwtCookie();
  }

  @Override
  public String generateJwtStr(UserDetailsImpl userDetails) {
    return jwtUtils.generateJwtToken(userDetails);
  }

  @Override
  public MobileSignInRes mobileSignIn(LoginRequest loginRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
    UserDetailsImpl userDetails = login(authentication);
    String jwt = jwtUtils.generateTokenFromUserName(userDetails.getUsername());
    List<String> roles =
        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    return new MobileSignInRes(userDetails.getId(), roles, jwt);
  }

  @Override
  public ResponseCookie generateResponseCookie(UserDetailsImpl userDetails) {
    return jwtUtils.generateJwtCookie(userDetails);
  }
}
