package com.example.PING.controller;

import com.example.PING.dto.request.UserLoginRequest;
import com.example.PING.dto.request.UserUpdateRequest;
import com.example.PING.dto.response.*;
import com.example.PING.dto.request.UserRequest;
import com.example.PING.service.UserService;
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
        if (response instanceof LoginResponse loginResponse) {
            return ResponseEntity.ok(loginResponse);  // HTTP 200 OK
        } else if (response instanceof ErrorResponse errorResponse) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);  // HTTP 401 Unauthorized
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/signup") // 회원가입 엔드포인트
    public ResponseEntity<?> signup(@RequestBody UserRequest request) {
        try {
            SignUpResponse newUserResponse = userService.signUp(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUserResponse); // HTTP 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage())); // HTTP 400 Bad Request
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailResponse> getMyPage(@PathVariable("userId") Long userId) { // My page 정보 조회
        Optional<UserDetailResponse> userResponse = userService.getUserDetail(userId);

        return userResponse
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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