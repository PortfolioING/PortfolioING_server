package com.example.PING.global.annotation;

import com.example.PING.error.exception.BusinessException;
import com.example.PING.error.exception.ResourceNotFoundException;
import com.example.PING.error.exception.ErrorCode;
import com.example.PING.user.entity.User;
import com.example.PING.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        final boolean isUserAuthAnnotation = parameter.getParameterAnnotation(AuthUser.class) != null;
        final boolean isUserClass = parameter.getParameterType().equals(User.class);
        return isUserAuthAnnotation && isUserClass;
    }

    @Override
    public User resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return getCurrentUser();
    }

    private User getCurrentUser() {
        return userRepository
                .findById(getCurrentUSerId())
                .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));
    }

    private Long getCurrentUSerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ErrorCode.AUTH_NOT_FOUND);
        }

        User principal = (User) authentication.getPrincipal();
        return principal.getUserId();
    }

}
