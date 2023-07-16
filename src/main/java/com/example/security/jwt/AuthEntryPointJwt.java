package com.example.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author BAO 7/12/2023
 */

/**
 * catch authentication error.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    /**
     * CrossOrigin  chỉ có tác dụng bên trong controller (controllers methods)
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
//    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "")
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.error("Unauthorized error: {}",authException.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Accept","application/json");
        response.setHeader("Access-Control-Allow-Origin","http://localhost:3000");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        final Map<String, Object> body =new HashMap<>();
        body.put("status",HttpStatus.UNAUTHORIZED.value());
        body.put("error", "Unauthorized");
        body.put("message",authException.getMessage());
        body.put("path",request.getServletPath());

        final ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
