package com.example.PING.user.controller;

import com.example.PING.user.dto.request.UserProfileUpdateRequest;
import com.example.PING.user.dto.response.*;
import com.example.PING.user.entity.User;
import com.example.PING.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "My page 정보 조회")
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getUserProfile(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(userService.getUserProfile(user.getUserId()));
    }

    @Operation(summary = "포트폴리오 id List", description = "로그인한 사용자의 포트폴리오 ID 리스트 조회")
    @GetMapping("/me/portfolio")
    public ResponseEntity<UserPortfolioIdListResponse> getUserPortfolioIdList(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(userService.getUserPortfolioIdList(user.getUserId()));
    }

    @Operation(summary = "My page 정보 수정")
    @PutMapping("/me")
    public ResponseEntity<UserUpdateResponse> updateUserProfile(
            @AuthenticationPrincipal User user,
            @RequestBody UserProfileUpdateRequest userProfileUpdateRequest) {

        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        UserUpdateResponse updatedUser = userService.updateUserProfile(user.getUserId(), userProfileUpdateRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "user 삭제")
    @DeleteMapping("/me")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        userService.deleteUser(user.getUserId());
        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted successfully");
        return ResponseEntity.ok(response);
    }
}