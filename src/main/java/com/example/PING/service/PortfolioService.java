package com.example.PING.service;

import com.example.PING.dto.PortfolioRequestDto;
import com.example.PING.dto.PortfolioResponseDto;
import com.example.PING.dto.UserPortfoliosResponse;
import com.example.PING.entity.*;
import com.example.PING.repository.*;
import com.example.PING.dto.request.PortfolioRequestDto;
import com.example.PING.dto.response.PortfolioResponseDto;
import com.example.PING.entity.Portfolio;
import com.example.PING.repository.PortfolioRepository;
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
    private final DomainRepository domainRepository;


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
        Portfolio portfolio = Portfolio.builder()
                .user(user)
                .survey(survey)
                .template(template)
                .title(portfolioRequestDto.getTitle())
                .description(portfolioRequestDto.getDescription())
                .build();

        //Todo 혹시 여기서 savedPortfolio 따로 담은 이유가 있나요?
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

        // 포트폴리오 내용 필드 업데이트
        portfolio.updatePortfolioContents(portfolioRequestDto.getTitle(), portfolioRequestDto.getDescription());

        return convertToResponseDto(portfolioRepository.save(portfolio));
    }

    @Transactional
    public void deletePortfolio(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolioId));

        // CascadeType.ALL -> 영속성 전이 설정으로 연관된 domain, survey 삭제 로직 X!

        // Portfolio 삭제
        portfolioRepository.delete(portfolio);
    }

    private PortfolioResponseDto convertToResponseDto(Portfolio portfolio) {
        return PortfolioResponseDto.builder()
                .portfolio(portfolio)
                .build();
    }

}
