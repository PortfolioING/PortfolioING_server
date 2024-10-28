package com.example.PING.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long userId;
    private String name;
    private String email;
    private String nickname;
    private String profilePic;
}
