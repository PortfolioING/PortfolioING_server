package com.example.PING.service;

import com.example.PING.dto.PortfolioRequestDto;
import com.example.PING.dto.PortfolioResponseDto;
import com.example.PING.entity.Portfolio;
import com.example.PING.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public PortfolioResponseDto createPortfolio(PortfolioRequestDto portfolioRequestDto) {
        Portfolio portfolio = new Portfolio();
        portfolio.setTitle(portfolioRequestDto.getTitle());
        portfolio.setDescription(portfolioRequestDto.getDescription());
        // 사용자 및 템플릿 설정은 추가적인 로직 필요
        return convertToResponseDto(portfolioRepository.save(portfolio));
    }

    @Transactional(readOnly = true)
    public List<PortfolioResponseDto> getAllPortfolios() {
        return portfolioRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PortfolioResponseDto getPortfolioById(Long portfolioId) {
        return convertToResponseDto(portfolioRepository.findById(portfolioId).orElse(null));
    }

    @Transactional
    public void deletePortfolio(Long portfolioId) {
        portfolioRepository.deleteById(portfolioId);
    }

    private PortfolioResponseDto convertToResponseDto(Portfolio portfolio) {
        PortfolioResponseDto dto = new PortfolioResponseDto();
        dto.setPortfolioId(portfolio.getPortfolioId());
        dto.setTitle(portfolio.getTitle());
        dto.setDescription(portfolio.getDescription());
        return dto;
    }
}
