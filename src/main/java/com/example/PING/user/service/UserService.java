package com.example.PING.user.service;

import com.example.PING.error.ResourceNotFoundException;
import com.example.PING.user.dto.response.*;
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
//    private final HttpSession httpSession;

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        return UserResponse.from(
                userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId))
        );
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }

    public UserLoginResponse login(UserLoginRequest request) {
        User targetUser = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with Email: " + request.email()));
        // 비밀번호 확인 로직 활성화
        if (!targetUser.getPassword().equals(request.password())) {
            new IllegalArgumentException("Password does not match. Your input: " + request.password());
        }
//            String token = generateToken(targetUser);  // JWT 토큰 생성 로직 (모의)
//            return new UserResponseDto(targetUser.getUserId(), targetUser.getName(), targetUser.getEmail(), token, targetUser.getProfilePic());
//        httpSession.setAttribute("user", targetUser.getUserId());
//        long loginId = Long.parseLong(httpSession.getAttribute("user").toString());
//        User loginUser = userRepository.findById(loginId)
//                .orElseThrow(()-> new IllegalArgumentException("User not found with id: "+ loginId));
        return new UserLoginResponse(
                targetUser.getUserId());
//                targetUser.getEmail(),
//                targetUser.getName(),
//                targetUser.getNickname(),
//                targetUser.getProfilePic());
    }

    private String generateToken(User user) {
        return "jwt_token_here";  // JWT 토큰 생성 로직
    }

    public UserSignUpResponse signUp(UserSignUpRequest request) {
        // 이미 존재하는 이메일인지 확인
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");  // 이메일 중복 처리
        }

        // 새 사용자 객체 생성
        User newUser = new User(request.password(), request.name(), request.email(), request.nickname(), request.userIcon());

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
