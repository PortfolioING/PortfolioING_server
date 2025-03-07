package com.example.PING.likes.controller;

import com.example.PING.likes.dto.request.LikesCreateRequest;
import com.example.PING.likes.dto.response.LikesResponse;
import com.example.PING.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikesController {
    private final LikesService likesService;

    @PostMapping
    public ResponseEntity<LikesResponse> createLike(@RequestBody LikesCreateRequest likesCreateRequest) {
        LikesResponse response = likesService.createLike(likesCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 추가적인 엔드포인트 정의 가능
}
