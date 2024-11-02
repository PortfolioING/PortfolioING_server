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
        Domain domain = Domain.builder()
                .portfolio(portfolio)
                .domain(domainRequestDto.getDomain())
                .build();

        Domain savedDomain = domainRepository.save(domain);

        // Portfolio 에도 Domain 설정
        portfolio.setDomain(savedDomain);
        portfolioRepository.save(portfolio);

        return convertToResponseDto(savedDomain);
    }

    @Transactional(readOnly = true)
    public DomainResponseDto getDomainByPortfolioId(Long portfolioId) {
        Domain domain = domainRepository.findByPortfolio_PortfolioId(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Domain not found for Portfolio ID: " + portfolioId));
        return convertToResponseDto(domain);
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
        Domain domain = domainRepository.findById(domainId)
                .orElseThrow(() -> new IllegalArgumentException("Domain not found with ID: " + domainId));

        // Todo 변경사항 전이되는지 확인
        // 해당 도메인을 사용하는 포트폴리오의 domain 필드를 null로 설정
        Portfolio portfolio = portfolioRepository.findByDomain(domain);
        if (portfolio != null) {
            portfolio.setDomain(null);
            portfolioRepository.save(portfolio);  // 변경사항 저장
        }

        // 도메인 삭제
        domainRepository.delete(domain);
    }

    private DomainResponseDto convertToResponseDto(Domain domain) {
        return DomainResponseDto.builder()
                .domain(domain)
                .build();
        }
}
