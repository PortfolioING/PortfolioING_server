package com.example.PING.controller;


import com.example.PING.dto.request.DomainRequestDto;
import com.example.PING.dto.response.DomainResponseDto;
import com.example.PING.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/domains")
@RequiredArgsConstructor
public class DomainController {

    private final DomainService domainService;

    // 새로운 도메인 등록
    @PostMapping
    public ResponseEntity<DomainResponseDto> createDomain(@RequestBody DomainRequestDto domainRequestDto) {
        if (domainRequestDto == null) System.out.println("domainRequestDto is null");
        else System.out.println(domainRequestDto);
        DomainResponseDto response = domainService.createDomain(domainRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 특정 포트폴리오의 도메인 조회
    @GetMapping
    public ResponseEntity<DomainResponseDto> getDomainByPortfolioId(@RequestParam("portfolio_id") Long portfolioId) {
        DomainResponseDto response = domainService.getDomainByPortfolioId(portfolioId);
        return ResponseEntity.ok(response);
    }

    // 특정 도메인 삭제
    @DeleteMapping("/{domain_id}")
    public ResponseEntity<Map<String, String>> deleteDomain(@PathVariable("domain_id") Long domainId) {
        domainService.deleteDomain(domainId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Domain deleted successfully");
        return ResponseEntity.ok(response);
    }
}

