package com.example.PING.controller;

import com.example.PING.dto.PortfolioRequestDto;
import com.example.PING.dto.PortfolioResponseDto;
import com.example.PING.dto.UserPortfoliosResponse;
import com.example.PING.service.PortfolioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    // (포트폴리오 생성) 설문 기반 새 포트폴리오 생성
    @PostMapping
    public ResponseEntity<PortfolioResponseDto> createPortfolio(@Valid @RequestBody PortfolioRequestDto portfolioRequestDto) {
        PortfolioResponseDto responseDto = portfolioService.createPortfolio(portfolioRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // (마이페이지_포트폴리오 조회) 특정 사용자의 포트폴리오 목록 조회
    @GetMapping
    public ResponseEntity<UserPortfoliosResponse> getUserPortfolios(@RequestParam("user_id") Long userId) {
        UserPortfoliosResponse response = portfolioService.getPortfoliosByUserId(userId);
        return ResponseEntity.ok(response);
    }

}
