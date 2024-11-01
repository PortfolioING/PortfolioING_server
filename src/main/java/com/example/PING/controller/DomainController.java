package com.example.PING.controller;


import com.example.PING.dto.DomainRequestDto;
import com.example.PING.dto.DomainResponseDto;
import com.example.PING.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
