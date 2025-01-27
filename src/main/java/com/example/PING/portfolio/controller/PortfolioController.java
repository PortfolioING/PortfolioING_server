package com.example.PING.portfolio.controller;

import com.example.PING.portfolio.dto.request.PortfolioCreateRequest;
import com.example.PING.portfolio.dto.request.PortfolioUpdateRequest;
import com.example.PING.portfolio.dto.response.PortfolioCreateResponse;
import com.example.PING.portfolio.dto.response.PortfolioResponse;
import com.example.PING.portfolio.dto.response.PortfolioDemoResponse;
import com.example.PING.portfolio.dto.response.PortfolioUpdateResponse;
import com.example.PING.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;


    // (포트폴리오 생성) 새 포트폴리오 생성
    @PostMapping
    public ResponseEntity<PortfolioCreateResponse> createPortfolio(@RequestBody PortfolioCreateRequest portfolioRequestDto) {
        PortfolioCreateResponse response = portfolioService.createPortfolio(portfolioRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // (마이페이지_포트폴리오 조회) 특정 포트폴리오의 (내워크스페이스) 데모 조회
    @GetMapping("/demo/{portfolio_id}")
    public ResponseEntity<PortfolioDemoResponse> getPortfolioDemo(@PathVariable("portfolio_id") Long portfolioId) {
        PortfolioDemoResponse response = portfolioService.getPortfolioDemo(portfolioId);
        return ResponseEntity.ok(response);
    }

    // (포트폴리오 상세 조회) 특정 포트폴리오의 세부 내용 조회
    @GetMapping("/{portfolio_id}")
    public ResponseEntity<PortfolioResponse> getPortfolioById(@PathVariable("portfolio_id") Long portfolioId) {
        PortfolioResponse response = portfolioService.getPortfolioById(portfolioId);
        return ResponseEntity.ok(response);
    }

    // (포트폴리오 수정) 포트폴리오 타이틀 이미지 변경
    @PutMapping("/title_img/{portfolio_id}")
    public ResponseEntity<PortfolioUpdateResponse> updatePortfolio(
            @PathVariable("portfolio_id") Long portfolioId,
            @RequestBody PortfolioUpdateRequest portfolioUpdateRequest) {
        PortfolioUpdateResponse response = portfolioService.updatePortfolio(portfolioId, portfolioUpdateRequest);
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
