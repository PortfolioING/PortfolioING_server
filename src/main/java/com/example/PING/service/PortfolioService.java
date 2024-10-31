package com.example.PING.service;

import com.example.PING.dto.PortfolioRequestDto;
import com.example.PING.dto.PortfolioResponseDto;
import com.example.PING.entity.Portfolio;
import com.example.PING.entity.Template;
import com.example.PING.entity.User;
import com.example.PING.repository.PortfolioRepository;
import com.example.PING.repository.TemplateRepository;
import com.example.PING.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final TemplateRepository templateRepository;

    @Transactional
    public PortfolioResponseDto createPortfolio(PortfolioRequestDto portfolioRequestDto) {
        Portfolio portfolio = new Portfolio();
        portfolio.setTitle(portfolioRequestDto.getTitle());
        portfolio.setDescription(portfolioRequestDto.getDescription());
        // 사용자 및 템플릿 설정은 추가적인 로직 필요
//        User user = userRepository.findById(portfolioRequestDto.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//        portfolio.setUser(user);
        User user = userRepository.findById(portfolioRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + portfolioRequestDto.getUserId()));
        portfolio.setUser(user);

        Template template = templateRepository.findById(portfolioRequestDto.getTemplateId())
                .orElseThrow(() -> new IllegalArgumentException("Template not found"));
        portfolio.setTemplate(template);

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
