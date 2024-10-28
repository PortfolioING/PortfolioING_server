package com.example.PING.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String name;
    private String email;
    private String password;
    private String nickname;
    private String profilePic;
}
