package com.example.PING.dto;

import com.example.PING.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long userId;
    private String name;
    private String email;
    private String nickname;
    private String profilePic;

    @Builder
    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profilePic = user.getProfilePic();
    }
}
