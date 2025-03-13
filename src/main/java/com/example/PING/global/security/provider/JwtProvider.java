package com.example.PING.global.security.provider;

import com.example.PING.error.exception.ErrorCode;
import com.example.PING.error.exception.JwtInvalidException;
import com.example.PING.global.security.token.JwtAuthenticationToken;
import com.example.PING.global.security.utils.JwtUtil;
import com.example.PING.user.entity.User;
import com.example.PING.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@RequiredArgsConstructor
public class JwtProvider implements AuthenticationProvider {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Claims claims = getClaims(authentication);
        final User user = getMemberById(claims.getSubject());

        return new JwtAuthenticationToken(
                user,
                "",
                List.of(new SimpleGrantedAuthority("ROLE_USER") // Todo 우리 코드에는 role이 따로 없으므로, 디폴트값으로 "ROLE_USER" 넣어줌
                ));
    }

    private Claims getClaims(Authentication authentication) {
        Claims claims;
        try {
            claims = jwtUtil.getAccessTokenClaims(authentication);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new JwtInvalidException(ErrorCode.EXPIRED_TOKEN.getMessage());
        } catch (SignatureException signatureException) {
            throw new JwtInvalidException(ErrorCode.WRONG_TYPE_TOKEN.getMessage());
        } catch (MalformedJwtException malformedJwtException) {
            throw new JwtInvalidException(ErrorCode.UNSUPPORTED_TOKEN.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new JwtInvalidException(ErrorCode.UNKNOWN_ERROR.getMessage());
        }
        return claims;
    }

    private User getMemberById(String id) {
        try {
            return userService.getUserById(Long.parseLong(id));
        } catch (Exception e) {
            throw new BadCredentialsException(ErrorCode.BAD_CREDENTIALS.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}