package com.example.PING.service;

import com.example.PING.dto.response.SignUpResponseDto;
import com.example.PING.dto.request.UserRequestDto;
import com.example.PING.dto.response.UserDetailResponseDto;
import com.example.PING.dto.response.UserResponseDto;
import com.example.PING.dto.response.UserUpdateResponseDto;
import com.example.PING.entity.User;
import com.example.PING.repository.UserRepository;
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

    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = User.builder()
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .nickname(userRequestDto.getNickname())
                .profilePic(userRequestDto.getProfilePic())
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

    public UserResponseDto login(UserRequestDto request) {
        Optional<User> targetUser = Optional.ofNullable(userRepository.findByEmail(request.email()));

        if (targetUser.isPresent()) {
            User user = targetUser.get();
            if (user.getPassword().equals(request.password())) {
                String token = generateToken(user);  // JWT 토큰 생성 로직 (모의)
                return new UserResponseDto(user.getUserId(), user.getName(), user.getEmail(), token, user.getProfilePic());
            }
        }
        // return new ErrorResponse("Invalid email or password"); //TODO 수정해야 함
        return null;
    }

    private String generateToken(User user) {
        return "jwt_token_here";  // JWT 토큰 생성 로직
    }

    public SignUpResponseDto signUp(UserRequestDto request) {
        // 이미 존재하는 이메일인지 확인
        if (userRepository.findByEmail(request.email()) != null) {
            throw new IllegalArgumentException("Email already in use");  // 이메일 중복 처리
        }

        // 새 사용자 객체 생성
        User newUser = new User();
        newUser.setName(request.name());
        newUser.setEmail(request.email());
        newUser.setPassword(request.password()); // 비밀번호는 해시 처리를 권장
        newUser.setNickname(request.nickname());
        newUser.setProfilePic(request.profilePic()); // 프로필 사진 URL 등
        newUser.setCreatedAt(LocalDateTime.now()); // 생성일자 설정
        newUser.setUpdatedAt(LocalDateTime.now()); // 업데이트일자 설정

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
    public UserUpdateResponseDto updateUser(Long userId, UserRequestDto userUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userUpdateRequest.name());
        user.setEmail(userUpdateRequest.email());
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
        return UserResponseDto.builder()
                .user(user)
                .build();
    }
}
