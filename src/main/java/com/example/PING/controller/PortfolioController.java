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
    public ResponseEntity<PortfolioResponseDto> createPortfolio(@RequestBody PortfolioRequestDto portfolioRequestDto) {
        PortfolioResponseDto response = portfolioService.createPortfolio(portfolioRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // (마이페이지_포트폴리오 조회) 특정 사용자의 포트폴리오 목록 조회
    @GetMapping
    public ResponseEntity<UserPortfoliosResponse> getUserPortfolios(@RequestParam("user_id") Long userId) {
        UserPortfoliosResponse response = portfolioService.getAllPortfolios(userId);
        return ResponseEntity.ok(response);
    }

    // (포트폴리오 상세 조회) 특정 포트폴리오의 세부 내용 조회
    @GetMapping("/{portfolio_id}")
    public ResponseEntity<PortfolioResponseDto> getPortfolioById(@PathVariable("portfolio_id") Long portfolioId) {
        PortfolioResponseDto response = portfolioService.getPortfolioById(portfolioId);
        return ResponseEntity.ok(response);
    }

    // (포트폴리오 수정) 포트폴리오 세부 내용 수정
    @PutMapping("/{portfolio_id}")
    public ResponseEntity<PortfolioResponseDto> updatePortfolio(
            @PathVariable("portfolio_id") Long portfolioId,
            @RequestBody PortfolioRequestDto portfolioRequestDto) {
        PortfolioResponseDto response = portfolioService.updatePortfolio(portfolioId, portfolioRequestDto);
        return ResponseEntity.ok(response);
    }

    // (포트폴리오 삭제) 특정 포트폴리오 삭제
    @DeleteMapping("/{portfolio_id}")
    public ResponseEntity<String> deletePortfolio(@PathVariable("portfolio_id") Long portfolioId) {
        portfolioService.deletePortfolio(portfolioId);
        String response = "Portfolio deleted successfully";
        return ResponseEntity.ok(response);
    }

}
