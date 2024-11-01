package com.example.PING.service;

import com.example.PING.dto.PortfolioRequestDto;
import com.example.PING.dto.PortfolioResponseDto;
import com.example.PING.dto.UserPortfoliosResponse;
import com.example.PING.entity.Portfolio;
import com.example.PING.entity.Survey;
import com.example.PING.entity.Template;
import com.example.PING.entity.User;
import com.example.PING.repository.PortfolioRepository;
import com.example.PING.repository.SurveyRepository;
import com.example.PING.repository.TemplateRepository;
import com.example.PING.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final TemplateRepository templateRepository;
    private final SurveyRepository surveyRepository;

    @Transactional
    public PortfolioResponseDto createPortfolio(PortfolioRequestDto portfolioRequestDto) {

        // User, Survey, Template 엔티티 조회
        User user = userRepository.findById(portfolioRequestDto.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + portfolioRequestDto.getUser_id()));
        Survey survey = surveyRepository.findById(portfolioRequestDto.getSurvey_id())
                .orElseThrow(() -> new IllegalArgumentException("Survey not found with ID: " + portfolioRequestDto.getSurvey_id()));
        Template template = templateRepository.findById(portfolioRequestDto.getTemplate_id())
                .orElseThrow(() -> new IllegalArgumentException("Template not found with ID: " + portfolioRequestDto.getTemplate_id()));

        // Portfolio 생성 및 설정
        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolio.setSurvey(survey);
        portfolio.setTemplate(template);
        portfolio.setTitle(portfolioRequestDto.getTitle());
        portfolio.setDescription(portfolioRequestDto.getDescription());
        portfolio.setCreatedAt(LocalDateTime.now());
        portfolio.setUpdatedAt(LocalDateTime.now());

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        // Survey 에도 Portfolio 설정
        survey.setPortfolio(savedPortfolio);
        surveyRepository.save(survey);

        return convertToResponseDto(portfolioRepository.save(portfolio));
    }


    // 특정 사용자의 포트폴리오 리스트 조회
    @Transactional(readOnly = true)
    public UserPortfoliosResponse getAllPortfolios(Long userId) {

        List<PortfolioResponseDto> portfolios = portfolioRepository.findAll().stream()
                .filter(portfolio -> portfolio.getUser().getUserId().equals(userId))
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());

        return new UserPortfoliosResponse(userId, portfolios);
    }

    @Transactional(readOnly = true)
    public PortfolioResponseDto getPortfolioById(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolioId));
        return convertToResponseDto(portfolio);    }

    @Transactional
    public PortfolioResponseDto updatePortfolio(Long portfolioId, PortfolioRequestDto portfolioRequestDto) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolioId));

        // 포트폴리오 필드 업데이트
        portfolio.setTitle(portfolioRequestDto.getTitle());
        portfolio.setDescription(portfolioRequestDto.getDescription());
        portfolio.setUpdatedAt(LocalDateTime.now());  // 수정 시간 갱신

        return convertToResponseDto(portfolioRepository.save(portfolio));
    }

    @Transactional
    public void deletePortfolio(Long portfolioId) {
        if (!portfolioRepository.existsById(portfolioId)) {
            throw new IllegalArgumentException("Portfolio not found with ID: " + portfolioId);
        }
        portfolioRepository.deleteById(portfolioId);
    }

    private PortfolioResponseDto convertToResponseDto(Portfolio portfolio) {
        PortfolioResponseDto dto = new PortfolioResponseDto();

        dto.setPortfolioId(portfolio.getPortfolioId());
        dto.setUserId(portfolio.getUser().getUserId());
        dto.setSurveyId(portfolio.getSurvey().getSurveyId());
        dto.setTemplateId(portfolio.getTemplate().getTemplateId());
        dto.setTitle(portfolio.getTitle());
        dto.setDescription(portfolio.getDescription());
        dto.setCreatedAt(portfolio.getCreatedAt());
        dto.setUpdatedAt(portfolio.getUpdatedAt());
        return dto;
    }

}
