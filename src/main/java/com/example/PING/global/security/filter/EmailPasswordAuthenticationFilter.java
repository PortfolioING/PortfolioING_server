package com.example.PING.global.security.filter;

import com.example.PING.error.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
//Todo 이거 일반로그인 용도로 나중에 사용할 것. 현재는 사용하지 않을 파일입니다.
public class EmailPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public EmailPasswordAuthenticationFilter(final AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//            throws AuthenticationException {
//        final UsernamePasswordAuthenticationToken authRequest;
//        try {
//            final AdminLoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), AdminLoginRequest.class);
//            authRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.email(), loginRequest.password());
//        } catch (IOException exception) {
//            throw new BadCredentialsException(ErrorCode.INVALID_INPUT_VALUE.getMessage());
//        }
//        setDetails(request, authRequest);
//        return this.getAuthenticationManager().authenticate(authRequest);
//    }
}