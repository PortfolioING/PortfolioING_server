package com.example.PING.portfolio.controller;

import com.example.PING.component.dto.response.ComponentTreeResponse;
import com.example.PING.component.service.ComponentService;
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
    private final ComponentService componentService;


    // (포트폴리오 생성) 새 포트폴리오 생성
    @PostMapping
    public ResponseEntity<PortfolioCreateResponse> createPortfolio(@RequestBody PortfolioCreateRequest portfolioRequestDto) {
        PortfolioCreateResponse response = portfolioService.createPortfolio(portfolioRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    @RequestBody(content = @Content(
//            encoding = @Encoding(name = "request", contentType = MediaType.APPLICATION_JSON_VALUE)))
//    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Test.Response uploadFile(
//            @RequestPart("files") @NotEmpty List<MultipartFile> files,
//            @RequestPart("request") @Valid Test.Request request
//    ) {
//        mockFileUploadService.upload(files, request);
//        return new Test.Response("success");
//    }

    // (마이페이지_포트폴리오 조회) 특정 포트폴리오의 (내워크스페이스) 데모 조회
    @GetMapping("/demo/{portfolio_id}")
    public ResponseEntity<PortfolioDemoResponse> getPortfolioDemo(@PathVariable("portfolio_id") Long portfolioId) {
        PortfolioDemoResponse response = portfolioService.getPortfolioDemo(portfolioId);
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
