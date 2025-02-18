package com.example.PING.like.controller;

import com.example.PING.like.dto.request.LikeCreateRequest;
import com.example.PING.like.dto.response.LikeResponse;
import com.example.PING.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeResponse> createLike(@RequestBody LikeCreateRequest likeCreateRequest) {
        LikeResponse response = likeService.createLike(likeCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 추가적인 엔드포인트 정의 가능
}
