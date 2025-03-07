package com.example.PING.user.service;

import com.example.PING.error.exception.ResourceNotFoundException;
import com.example.PING.user.dto.response.*;
import com.example.PING.user.entity.OauthInfo;
import com.example.PING.user.repository.UserRepository;
import com.example.PING.user.dto.request.UserLoginRequest;
import com.example.PING.user.dto.request.UserSignUpRequest;
import com.example.PING.user.dto.request.UserProfileUpdateRequest;
import com.example.PING.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));
    }

    public User getUserByOAuthInfo(OauthInfo oauthInfo) { // 소셜로그인 유저 찾기
        return userRepository.findByOauthInfo(oauthInfo)
                .orElseGet(() -> createTempUser(oauthInfo));
    }


    public User createTempUser(OauthInfo oauthInfo) { // 임시 회원 생성
        User tempUser = User.createTemporalUser(oauthInfo);
        return tempUser;
    }

    public User saveTempUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }

    public UserLoginResponse login(UserLoginRequest request) { // 일반 로그인
        User targetUser = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with Email: " + request.email()));
        // 비밀번호 확인 로직 활성화
        if (!targetUser.getPassword().equals(request.password())) {
            throw new IllegalArgumentException("Password does not match. Your input: " + request.password());
        }
        return new UserLoginResponse(
                targetUser.getUserId());
//                targetUser.getEmail(),
//                targetUser.getName(),
//                targetUser.getNickname(),
//                targetUser.getProfilePic());
    }

    public UserSignUpResponse signUp(UserSignUpRequest request) { // 일반 회원가입
        // 이미 존재하는 이메일인지 확인
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");  // 이메일 중복 처리
        }

        // 새 사용자 객체 생성
        User newUser = new User(request.password(), request.name(), request.email(), request.nickname(), request.userIcon(), null);

        // 사용자 저장
        userRepository.save(newUser);

        return UserSignUpResponse.from(newUser);
    }

    public Optional<UserProfileResponse> getUserProfile(Long userId) { //My page 정보 조회
        return userRepository.findById(userId)
                .map(UserProfileResponse :: from);
    }

    public Optional<UserPortfolioIdListResponse> getUserPortfolioIdList(Long userId) {
        return userRepository.findById(userId).map(UserPortfolioIdListResponse::from);
    }

    @Transactional
    public UserUpdateResponse updateUserProfile(Long userId, UserProfileUpdateRequest userProfileUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.changeName(userProfileUpdateRequest.name());
        user.changeNickName(userProfileUpdateRequest.nickname());
        user.changeUserIcon(userProfileUpdateRequest.userIcon());
        user.changePassword(userProfileUpdateRequest.password());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return UserUpdateResponse.from(user);
    }
}
