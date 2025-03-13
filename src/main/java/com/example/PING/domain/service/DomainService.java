package com.example.PING.domain.service;

import com.example.PING.domain.dto.request.DomainRequest;
import com.example.PING.domain.dto.response.DomainResponse;
import com.example.PING.domain.entity.Domain;
import com.example.PING.portfolio.entity.Portfolio;
import com.example.PING.error.exception.ResourceNotFoundException;
import com.example.PING.domain.repository.DomainRepository;
import com.example.PING.portfolio.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DomainService {
    private final DomainRepository domainRepository;
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public DomainResponse createDomain(DomainRequest domainRequest) {
        // Portfolio 조회
        Portfolio portfolio = portfolioRepository.findById(domainRequest.portfolio_id())
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found with ID: " + domainRequest.portfolio_id()));

        // Domain 생성 및 Portfolio 설정
        Domain domain = Domain.builder()
                .portfolio(portfolio)
                .domain(domainRequest.domain())
                .build();

        Domain savedDomain = domainRepository.save(domain);

        // Portfolio 에도 Domain 설정
        portfolio.saveDomainToPortfolio(savedDomain); // 생성한 도메인을 연결된 포트폴리오에 저장해두기
        portfolioRepository.save(portfolio);

        return DomainResponse.from(savedDomain);
    }

    @Transactional(readOnly = true)
    public DomainResponse getDomainByPortfolioId(Long portfolioId) {
        Domain domain = domainRepository.findByPortfolio_PortfolioId(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Domain not found for Portfolio ID: " + portfolioId));
        return DomainResponse.from(domain);
    }

    @Transactional(readOnly = true)
    public List<DomainResponse> getAllDomains() {
        return domainRepository.findAll().stream()
                .map(DomainResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DomainResponse getDomainById(Long domainId) {
        return domainRepository.findById(domainId)
                .map(DomainResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Domain with ID " + domainId + " not found"));
    }

    @Transactional
    public void deleteDomain(Long domainId) {
        Domain domain = domainRepository.findById(domainId)
                .orElseThrow(() -> new ResourceNotFoundException("Domain not found with ID: " + domainId));

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
