//package com.campusconnect.CampusConnect.jwt;
//// Provides custom handling for unauthorized requests, typically when authentication is required but not supplied or valid
//// When an unauthorized request id detected, it logs the error and returns a JSON response with an error message, status code, and path attempted.
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//@Slf4j
//public class AuthEntryPointJwt implements AuthenticationEntryPoint {
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        log.error("Unauthorized error: {}", authException.getMessage());
//
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//        Map<String, Object> responseBody = new HashMap<>();
//        responseBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
//        responseBody.put("error", "Unauthorized");
//        responseBody.put("message", "Access is denied. Please provide valid credentials.");
//        responseBody.put("path", request.getServletPath());
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(response.getOutputStream(), responseBody);
//    }
//}
