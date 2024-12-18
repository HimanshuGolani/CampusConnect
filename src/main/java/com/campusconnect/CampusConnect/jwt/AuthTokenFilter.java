//package com.campusconnect.CampusConnect.jwt;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class AuthTokenFilter implements Filter {
//
//    private final JwtUtils jwtUtils;
//    private final UserDetailsService userDetailsService;
//
//    @Autowired
//    public AuthTokenFilter(@Lazy JwtUtils jwtUtils, @Lazy UserDetailsService userDetailsService) {
//        this.jwtUtils = jwtUtils;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//
//        try {
//            // Extract JWT token from the Authorization header
//            String jwt = jwtUtils.getJwtFromHeader(httpServletRequest);
//
//            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//                // Get email from the JWT token
//                String emailFromJwtToken = jwtUtils.getEmailFromJwtToken(jwt);
//
//                // Load user details using the email from the token
//                UserDetails userDetails = userDetailsService.loadUserByUsername(emailFromJwtToken);
//
//                // Set the authentication in the Spring Security context
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        } catch (Exception e) {
//            // Handle any unexpected exceptions (e.g., invalid token)
//            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            httpServletResponse.getWriter().write("Error during JWT validation: " + e.getMessage());
//            return;
//        }
//
//        // Continue the filter chain
//        chain.doFilter(request, response);
//    }
//}
