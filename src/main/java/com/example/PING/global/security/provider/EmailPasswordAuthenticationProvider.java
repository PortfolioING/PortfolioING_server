package com.example.PING.global.security.provider;

import com.example.PING.error.exception.ErrorCode;
import com.example.PING.user.entity.User;
import com.example.PING.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@RequiredArgsConstructor
public class EmailPasswordAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        final String userEmail = token.getName();
        final String userPassword = (String) token.getCredentials();

        validateEmail(userEmail);
        final User user = getUserByEmail(userEmail);
        if (!user.isMatchingPassword(userPassword)) {
            throw new BadCredentialsException(ErrorCode.BAD_CREDENTIALS.getMessage());
        }

        return UsernamePasswordAuthenticationToken.authenticated(
                user,
                userPassword,
                List.of(new SimpleGrantedAuthority(null) // Todo 우리 코드에서는 role이 없음. null로 넣어보고 안 되면 role 개념 추가할 것
                ));
    }

    private static void validateEmail(String userEmail) {
        if (!EmailValidator.getInstance().isValid(userEmail)) {
            throw new BadCredentialsException(ErrorCode.BAD_CREDENTIALS.getMessage());
        }
    }

    private User getUserByEmail(String userEmail) {
        try {
            return userService.getUserByEmail(userEmail);
        } catch (Exception e) {
            throw new BadCredentialsException(ErrorCode.BAD_CREDENTIALS.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}