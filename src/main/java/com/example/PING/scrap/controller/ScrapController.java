package com.example.PING.scrap.controller;

import com.example.PING.scrap.dto.request.ScrapCreateRequest;
import com.example.PING.scrap.dto.response.ScrapResponse;
import com.example.PING.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scraps")
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping
    public ResponseEntity<ScrapResponse> createScrap(@RequestBody ScrapCreateRequest scrapCreateRequest) {
        ScrapResponse response = scrapService.createScrap(scrapCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 추가적인 엔드포인트 정의 가능
}
