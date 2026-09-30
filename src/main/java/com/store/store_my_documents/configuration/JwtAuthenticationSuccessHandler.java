package com.store.store_my_documents.configuration;



import java.io.IOException;
import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.springframework.stereotype.Component;

import com.store.store_my_documents.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    public JwtAuthenticationSuccessHandler(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException {

        String token = jwtService.generateToken(authentication);
        
        ResponseCookie cookie = ResponseCookie
                .from("jwt", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );

       
            response.sendRedirect("/store/MyDocuments");
        
    }
}