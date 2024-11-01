package com.example.PING.service;

import com.example.PING.dto.DomainRequestDto;
import com.example.PING.dto.DomainResponseDto;
import com.example.PING.entity.Domain;
import com.example.PING.entity.Portfolio;
import com.example.PING.repository.DomainRepository;
import com.example.PING.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DomainService {
    private final DomainRepository domainRepository;
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public DomainResponseDto createDomain(DomainRequestDto domainRequestDto) {
        // Portfolio 조회
        Portfolio portfolio = portfolioRepository.findById(domainRequestDto.getPortfolio_id())
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + domainRequestDto.getPortfolio_id()));

        // Domain 생성 및 Portfolio 설정
        Domain domain = new Domain();
        domain.setPortfolio(portfolio);
        domain.setDomain(domainRequestDto.getDomain());
        domain.setCreatedAt(LocalDateTime.now());

        Domain savedDomain = domainRepository.save(domain);

        return convertToResponseDto(savedDomain);
    }


    @Transactional(readOnly = true)
    public List<DomainResponseDto> getAllDomains() {
        return domainRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DomainResponseDto getDomainById(Long domainId) {
        return convertToResponseDto(domainRepository.findById(domainId).orElse(null));
    }

    @Transactional
    public void deleteDomain(Long domainId) {
        domainRepository.deleteById(domainId);
    }

    private DomainResponseDto convertToResponseDto(Domain domain) {
        DomainResponseDto dto = new DomainResponseDto();
        dto.setDomain_id(domain.getDomainId());
        dto.setPortfolio_id(domain.getPortfolio().getPortfolioId());
        dto.setDomain(domain.getDomain());
        dto.setCreatedAt(domain.getCreatedAt());
        return dto;
    }
}
