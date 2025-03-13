package com.example.PING.user.controller;

import com.example.PING.user.dto.request.UserProfileUpdateRequest;
import com.example.PING.user.dto.response.*;
import com.example.PING.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "My page 정보 조회"
    )
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable("userId") Long userId) { // My page 정보 조회
        Optional<UserProfileResponse> userResponse = userService.getUserProfile(userId);

        return userResponse
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "포트폴리오 id List",
            description = "특정 user의 포트폴리오 id 리스트 get"
    )
    @GetMapping("{user_id}/portfolio")
    public ResponseEntity<UserPortfolioIdListResponse> getUserPortfolioIdList(@PathVariable("user_id") Long userId) {
        Optional<UserPortfolioIdListResponse> userResponse = userService.getUserPortfolioIdList(userId);

        return userResponse.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "My page 정보 수정"
    )
    @PutMapping("/{userId}")
    public ResponseEntity<UserUpdateResponse> updateUserProfile( // My page 정보 수정
                                                          @PathVariable("userId") Long userId,
                                                          @RequestBody UserProfileUpdateRequest userProfileUpdateRequest) {

        UserUpdateResponse updatedUser = userService.updateUserProfile(userId, userProfileUpdateRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(
            summary = "user 삭제"
    )
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) { // User 삭제
        userService.deleteUser(userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted successfully");
        return ResponseEntity.ok(response);
    }
}