package com.example.PING.dto.response;


import lombok.Builder;

public record UserResponseDto(
        Long userId,
        String name,
        String email,
        String nickname,
        String profilePic
) {
    @Builder
    public UserResponseDto(Long userId, String name, String email, String nickname, String profilePic) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.profilePic = profilePic;
    }
}
