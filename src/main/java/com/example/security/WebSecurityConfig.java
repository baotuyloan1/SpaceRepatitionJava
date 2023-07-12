package com.example.security;

import com.example.security.jwt.AuthEntryPointJwt;
import com.example.security.services.UserDetailsServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.util.logging.Logger;

/**
 * @author BAO 7/12/2023
 */
@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

  @Value("${spring.h2.console.path}")
  private String h2ConsolePath;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  private AuthEntryPointJwt unauthorizedHandler

}
