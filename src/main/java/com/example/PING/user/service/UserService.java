package com.example.PING.user.service;

import com.example.PING.error.exception.ResourceNotFoundException;
import com.example.PING.user.dto.response.*;
import com.example.PING.user.entity.OauthInfo;
import com.example.PING.user.repository.UserRepository;
import com.example.PING.user.dto.request.UserProfileUpdateRequest;
import com.example.PING.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    public User getUserByOAuthInfo(OauthInfo oauthInfo) { // 소셜로그인 유저 찾기
        return userRepository.findByOauthInfo(oauthInfo)
                .orElseGet(() -> createTempUser(oauthInfo));
    }

    public User createTempUser(OauthInfo oauthInfo) { // 임시 회원 생성
        return User.createTemporalUser(oauthInfo);
    }

    public User saveTempUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(Long userId) { //My page 정보 조회
        User user = getUserById(userId);
        return UserProfileResponse.from(user);
    }

    @Transactional(readOnly = true)
    public UserPortfolioIdListResponse getUserPortfolioIdList(Long userId) {
        User user = getUserById(userId);
        return UserPortfolioIdListResponse.from(user);
    }

    @Transactional
    public UserUpdateResponse updateUserProfile(Long userId, UserProfileUpdateRequest userProfileUpdateRequest) {
        User user = getUserById(userId);

        user.changeNickName(userProfileUpdateRequest.nickname());
        user.changeUserIcon(userProfileUpdateRequest.userIcon());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return UserUpdateResponse.from(user);
    }
}