package com.example.PING.service;

import com.example.PING.dto.request.DomainRequestDto;
import com.example.PING.dto.response.DomainResponseDto;
import com.example.PING.entity.Domain;
import com.example.PING.entity.Portfolio;
import com.example.PING.repository.DomainRepository;
import com.example.PING.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DomainService {
    private final DomainRepository domainRepository;
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public DomainResponseDto createDomain(DomainRequestDto domainRequestDto) {
        // Portfolio 조회
        Portfolio portfolio = portfolioRepository.findById(domainRequestDto.portfolio_id())
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + domainRequestDto.portfolio_id()));

        // Domain 생성 및 Portfolio 설정
        Domain domain = Domain.builder()
                .portfolio(portfolio)
                .domain(domainRequestDto.domain())
                .build();

        Domain savedDomain = domainRepository.save(domain);

        // Portfolio 에도 Domain 설정
        portfolio.saveDomainToPortfolio(savedDomain); // 생성한 도메인을 연결된 포트폴리오에 저장해두기
        portfolioRepository.save(portfolio);

        return DomainResponseDto.from(savedDomain);
    }

    @Transactional(readOnly = true)
    public DomainResponseDto getDomainByPortfolioId(Long portfolioId) {
        Domain domain = domainRepository.findByPortfolio_PortfolioId(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Domain not found for Portfolio ID: " + portfolioId));
        return DomainResponseDto.from(domain);
    }

    @Transactional(readOnly = true)
    public List<DomainResponseDto> getAllDomains() {
        return domainRepository.findAll().stream()
                .map(DomainResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DomainResponseDto getDomainById(Long domainId) {
        return domainRepository.findById(domainId)
                .map(DomainResponseDto::from)
                .orElseThrow(() -> new IllegalArgumentException("Domain with ID " + domainId + " not found"));
    }

    @Transactional
    public void deleteDomain(Long domainId) {
        Domain domain = domainRepository.findById(domainId)
                .orElseThrow(() -> new IllegalArgumentException("Domain not found with ID: " + domainId));

        // Todo 변경사항 전이되는지 확인
        // 해당 도메인을 사용하는 포트폴리오의 domain 필드를 null로 설정
        Portfolio portfolio = portfolioRepository.findByDomain(domain);
        if (portfolio != null) {
            portfolio.setDomainNull(); // 삭제 대상인 도메인을 사용하고 있는 포트폴리오에 대해서 도메인값을 null로 설정함.
            portfolioRepository.save(portfolio);  // 변경사항 저장
        }

        // 도메인 삭제
        domainRepository.delete(domain);
    }
}
