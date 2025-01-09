package com.example.PING.portfolio.controller;

import com.example.PING.portfolio.dto.request.PortfolioCreateRequest;
import com.example.PING.portfolio.dto.request.PortfolioRequest;
import com.example.PING.portfolio.dto.request.PortfolioUpdateTemplateRequest;
import com.example.PING.portfolio.dto.response.PortfolioCreateResponse;
import com.example.PING.portfolio.dto.response.PortfolioResponse;
import com.example.PING.portfolio.dto.response.PortfolioUpdateTemplateResponse;
import com.example.PING.user.dto.response.UserPortfoliosResponse;
import com.example.PING.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;


    // (포트폴리오 생성) 설문 기반 새 포트폴리오 생성
    @PostMapping
    public ResponseEntity<PortfolioCreateResponse> createPortfolio(@RequestBody PortfolioCreateRequest portfolioRequestDto) {
        PortfolioCreateResponse response = portfolioService.createPortfolio(portfolioRequestDto);
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
    public ResponseEntity<PortfolioResponse> getPortfolioById(@PathVariable("portfolio_id") Long portfolioId) {
        PortfolioResponse response = portfolioService.getPortfolioById(portfolioId);
        return ResponseEntity.ok(response);
    }

    // (포트폴리오 수정) 포트폴리오 세부 내용 수정
    @PutMapping("/{portfolio_id}")
    public ResponseEntity<PortfolioResponse> updatePortfolio(
            @PathVariable("portfolio_id") Long portfolioId,
            @RequestBody PortfolioRequest portfolioRequest) {
        PortfolioResponse response = portfolioService.updatePortfolio(portfolioId, portfolioRequest);
        return ResponseEntity.ok(response);
    }

    // (포트폴리오 수정) 포트폴리오 세부 내용 수정
    @PutMapping("/template/{portfolio_id}")
    public ResponseEntity<PortfolioUpdateTemplateResponse> updateTemplate(
            @PathVariable("portfolio_id") Long portfolioId,
            @RequestBody PortfolioUpdateTemplateRequest portfolioRequestDto) {
        PortfolioUpdateTemplateResponse response = portfolioService.updateTemplate(portfolioId, portfolioRequestDto);
        return ResponseEntity.ok(response);
    }

    // (포트폴리오 삭제) 특정 포트폴리오 삭제
    @DeleteMapping("/{portfolio_id}")
    public ResponseEntity<Map<String, String>> deletePortfolio(@PathVariable("portfolio_id") Long portfolioId) {
        portfolioService.deletePortfolio(portfolioId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Portfolio deleted successfully");
        return ResponseEntity.ok(response);
    }

}
