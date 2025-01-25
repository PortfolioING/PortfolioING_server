package com.example.PING.user.controller;

import com.example.PING.user.dto.request.UserLoginRequest;
import com.example.PING.user.dto.request.UserSignUpRequest;
import com.example.PING.user.dto.request.UserUpdateRequest;
import com.example.PING.user.dto.response.*;
import com.example.PING.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        Object response = userService.login(request);
        if (response instanceof UserLoginResponse userLoginResponse) {
            return ResponseEntity.ok(userLoginResponse);  // HTTP 200 OK
        } else if (response instanceof ErrorResponse errorResponse) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);  // HTTP 401 Unauthorized
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping// 회원가입
    public ResponseEntity<?> signup(@RequestBody UserSignUpRequest request) {
        try {
            UserSignUpResponse newUserResponse = userService.signUp(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUserResponse); // HTTP 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage())); // HTTP 400 Bad Request
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable("userId") Long userId) { // My page 정보 조회
        Optional<UserProfileResponse> userResponse = userService.getUserProfile(userId);

        return userResponse
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("{user_id}/portfolio")
    public ResponseEntity<UserPortfolioIdListResponse> getUserPortfolioIdList(@PathVariable("user_id") Long userId) {
        Optional<UserPortfolioIdListResponse> userResponse = userService.getUserPortfolioIdList(userId);

        return userResponse.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserUpdateResponse> updateUser( // My page 정보 수정
                                                          @PathVariable("userId") Long userId,
                                                          @RequestBody UserUpdateRequest userUpdateRequest) {

        UserUpdateResponse updatedUser = userService.updateUser(userId, userUpdateRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) { // User 삭제
        userService.deleteUser(userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted successfully");
        return ResponseEntity.ok(response);
    }
}