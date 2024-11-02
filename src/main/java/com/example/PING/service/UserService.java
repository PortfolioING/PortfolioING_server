package com.example.PING.service;

import com.example.PING.dto.UserRequestDto;
import com.example.PING.dto.UserResponseDto;
import com.example.PING.entity.User;
import com.example.PING.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        userRepository.deleteById(userId);
    }

    private UserResponseDto convertToResponseDto(User user) {
        return UserResponseDto.builder()
                .user(user)
                .build();
    }
}
