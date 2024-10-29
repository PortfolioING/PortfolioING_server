package com.example.PING.controller;

import com.example.PING.dto.PortfolioRequestDto;
import com.example.PING.dto.PortfolioResponseDto;
import com.example.PING.service.PortfolioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
