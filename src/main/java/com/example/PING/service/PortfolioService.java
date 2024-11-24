package com.example.PING.service;

import com.example.PING.dto.request.PortfolioCreateRequestDto;
import com.example.PING.dto.request.PortfolioRequestDto;
import com.example.PING.dto.response.PortfolioCreateResponseDto;
import com.example.PING.dto.response.PortfolioResponseDto;
import com.example.PING.dto.response.UserPortfoliosResponse;
import com.example.PING.entity.*;
import com.example.PING.repository.*;
import com.example.PING.dto.request.PortfolioRequestDto;
import com.example.PING.dto.response.PortfolioResponseDto;
import com.example.PING.entity.Portfolio;
import com.example.PING.repository.PortfolioRepository;
import jakarta.servlet.http.HttpSession;
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
    private final SurveyRepository surveyRepository;
    private final DomainRepository domainRepository;
//    private final HttpSession httpSession;


    @Transactional
    public PortfolioCreateResponseDto createPortfolio(PortfolioCreateRequestDto requestDto) {

        // User, Survey, Template 엔티티 조회
        User user = userRepository.findById(requestDto.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + requestDto.getUser_id()));
        Survey survey = surveyRepository.findById(requestDto.getSurvey_id())
                .orElseThrow(() -> new IllegalArgumentException("Survey not found with ID: " + requestDto.getSurvey_id()));
//        Template template = templateRepository.findById(requestDto.getTemplate_id())
//                .orElseThrow(() -> new IllegalArgumentException("Template not found with ID: " + requestDto.getTemplate_id()));

//        long loginId = Long.parseLong(httpSession.getAttribute("user").toString());
//        User loginUser = userRepository.findById(loginId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + loginId));

        // Portfolio 생성 및 설정
        Portfolio portfolio = Portfolio.builder()
//                .user(loginUser)
                .user(user)
                .survey(survey)
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .image(requestDto.getImage())
                .build();

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        // Survey 에도 Portfolio 설정
        survey.setPortfolio(savedPortfolio);
        surveyRepository.save(survey);

        return PortfolioCreateResponseDto.builder()
                .portfolioId(savedPortfolio.getPortfolioId())
                .createdAt(savedPortfolio.getCreatedAt())
                .build();
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
        if (portfolioRequestDto.getTitle() != null) {
            portfolio.setTitle(portfolioRequestDto.getTitle());
        }
        if (portfolioRequestDto.getDescription() != null) {
            portfolio.setDescription(portfolioRequestDto.getDescription());
        }
        if (portfolioRequestDto.getMainColor() != null) {
            portfolio.setMainColor(portfolioRequestDto.getMainColor());
        }
        if (portfolioRequestDto.getSubColor() != null) {
            portfolio.setSubColor(portfolioRequestDto.getSubColor());
        }
        if (portfolioRequestDto.getBackgroundColor() != null) {
            portfolio.setBackgroundColor(portfolioRequestDto.getBackgroundColor());
        }

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
