package com.example.PING.service;

import com.example.PING.dto.request.UserLoginRequestDto;
import com.example.PING.dto.request.UserUpdateRequestDto;
import com.example.PING.dto.response.*;
import com.example.PING.dto.request.UserRequestDto;
import com.example.PING.entity.User;
import com.example.PING.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponse;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private final HttpSession httpSession;

    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = User.builder()
                .name(userRequestDto.name())
                .email(userRequestDto.email())
                .password(userRequestDto.password())
                .nickname(userRequestDto.nickname())
                .profilePic(userRequestDto.profilePic())
                .build();
        return convertToResponseDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long userId) {
        return convertToResponseDto(userRepository.findById(userId).orElse(null));
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    private UserResponseDto convertToResponseDto(User user) {
        return new UserResponseDto(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getNickname(),
                user.getProfilePic()
        );
    }

    public LoginResponseDto login(UserLoginRequestDto request) {
        User targetUser = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found with Email: " + request.email()));
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
        return new LoginResponseDto(
                targetUser.getUserId());
//                targetUser.getEmail(),
//                targetUser.getName(),
//                targetUser.getNickname(),
//                targetUser.getProfilePic());
    }

    private String generateToken(User user) {
        return "jwt_token_here";  // JWT 토큰 생성 로직
    }

    public SignUpResponseDto signUp(UserRequestDto request) {
        // 이미 존재하는 이메일인지 확인
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");  // 이메일 중복 처리
        }

        // 새 사용자 객체 생성
        User newUser = new User(request.password(), request.name(), request.email(), request.nickname(), request.profilePic());

        // 사용자 저장
        userRepository.save(newUser);

        return new SignUpResponseDto(newUser.getUserId(), newUser.getName(),
                newUser.getEmail(), newUser.getNickname(), newUser.getProfilePic(),
                newUser.getCreatedAt(), newUser.getUpdatedAt());
    }

    public Optional<UserDetailResponseDto> getUserDetail(Long userId) { //My page 정보 조회
        return userRepository.findById(userId)
                .map(user -> new UserDetailResponseDto(
                        user.getUserId(),
                        user.getName(),
                        user.getEmail(),
                        user.getNickname(),
                        user.getProfilePic(),
                        user.getPortfolioIds(),
                        user.getCreatedAt(),
                        user.getUpdatedAt()
                ));
    }

    @Transactional
    public UserUpdateResponseDto updateUser(Long userId, UserUpdateRequestDto userUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userUpdateRequest.name());
        user.setNickname(userUpdateRequest.nickname());
        user.setProfilePic(userUpdateRequest.profilePic());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return new UserUpdateResponseDto(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getNickname(),
                user.getProfilePic(),
                user.getUpdatedAt()
        );
    }
}
