package com.example.PING.portfolio.controller;

import com.example.PING.component.dto.response.ComponentTreeResponse;
import com.example.PING.component.service.ComponentService;
import com.example.PING.image.S3ImageService;
import com.example.PING.portfolio.dto.request.PortfolioCreateRequest;
import com.example.PING.portfolio.dto.response.PortfolioCreateResponse;
import com.example.PING.portfolio.dto.response.PortfolioDemoResponse;
import com.example.PING.portfolio.dto.response.PortfolioPageResponse;
import com.example.PING.portfolio.dto.response.PortfolioUpdateResponse;
import com.example.PING.portfolio.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final ComponentService componentService;
    private final S3ImageService s3ImageService;


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

    // (전체 포트폴리오 조회) 포트폴리오의 특정 페이지 조회 (최신순 / 좋아요순)
    @GetMapping("/page")
    public ResponseEntity<PortfolioPageResponse> getSortedPortfolios(
            @RequestParam(defaultValue = "0") int page,     // 페이지 번호
            @RequestParam(defaultValue = "50") int size,    // 한 페이지에서 가져올 Portfolio 개수
            @RequestParam(defaultValue = "latest") String sort) {   // "latest" or "likes"
        Pageable pageable = PageRequest.of(page, size);
        PortfolioPageResponse response = portfolioService.getPortfoliosSorted(pageable, sort);

        return ResponseEntity.ok(response);
    }

    // (포트폴리오 상세 조회) 특정 포트폴리오의 세부 내용 조회
    @GetMapping("/{portfolio_id}")
    public ResponseEntity<ComponentTreeResponse> getPortfolioComponentTree(@PathVariable("portfolio_id") Long portfolioId) {
        try {
            ComponentTreeResponse tree = componentService.getComponentTree(portfolioId);
            return ResponseEntity.ok(tree);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // (포트폴리오 수정) 포트폴리오 타이틀 이미지 변경
    @PutMapping(value = "/title_img/{portfolio_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "포트폴리오 타이틀 이미지 변경")
    public ResponseEntity<PortfolioUpdateResponse> updatePortfolioTitleImg(
            @PathVariable("portfolio_id") Long portfolioId,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        String imageURL = s3ImageService.upload(image);
        PortfolioUpdateResponse response = portfolioService.updatePortfolioTitleImg(portfolioId, imageURL);
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
