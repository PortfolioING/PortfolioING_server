package com.example.PING.service;

import com.example.PING.dto.request.PortfolioCreateRequestDto;
import com.example.PING.dto.request.PortfolioRequestDto;
import com.example.PING.dto.request.PortfolioUpdateTemplateRequestDto;
import com.example.PING.dto.response.*;
import com.example.PING.entity.*;
import com.example.PING.repository.*;
import com.example.PING.dto.response.PortfolioResponseDto;
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
    private final UserRepository userRepository;
    private final TemplateRepository templateRepository;
    private final SurveyRepository surveyRepository;
    private final DomainRepository domainRepository;
//    private final HttpSession httpSession;


    @Transactional
    public PortfolioCreateResponseDto createPortfolio(PortfolioCreateRequestDto requestDto) {

        // User, Survey, Template 엔티티 조회
        User user = userRepository.findById(requestDto.user_id())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + requestDto.user_id()));
        Survey survey = surveyRepository.findById(requestDto.survey_id())
                .orElseThrow(() -> new IllegalArgumentException("Survey not found with ID: " + requestDto.survey_id()));
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
                .title(requestDto.title())
                .description(requestDto.description())
                .image(requestDto.image())
                .build();

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        // Survey 에도 Portfolio 설정
        survey.setPortfolio(savedPortfolio);
        surveyRepository.save(survey);

        return new PortfolioCreateResponseDto(
                savedPortfolio.getPortfolioId(),
                savedPortfolio.getCreatedAt()
        );
    }

    // 특정 사용자의 포트폴리오 리스트 조회
    @Transactional(readOnly = true)
    public UserPortfoliosResponse getAllPortfolios(Long userId) {

        List<PortfolioResponseDto> portfolios = portfolioRepository.findAll().stream()
                .filter(portfolio -> portfolio.getUser().getUserId().equals(userId))
                .map(PortfolioResponseDto::from)
                .collect(Collectors.toList());

        return new UserPortfoliosResponse(userId, portfolios);
    }


    @Transactional(readOnly = true)
    public PortfolioResponseDto getPortfolioById(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolioId));
        return PortfolioResponseDto.from(portfolio);
    }

    @Transactional
    public PortfolioResponseDto updatePortfolio(Long portfolioId, PortfolioRequestDto portfolioRequestDto) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolioId));

        // 포트폴리오 내용 필드 업데이트
        if (portfolioRequestDto.title() != null) {
            portfolio.setTitle(portfolioRequestDto.title());
        }
        if (portfolioRequestDto.description() != null) {
            portfolio.setDescription(portfolioRequestDto.description());
        }
        if (portfolioRequestDto.mainColor() != null) {
            portfolio.setMainColor(portfolioRequestDto.mainColor());
        }
        if (portfolioRequestDto.subColor() != null) {
            portfolio.setSubColor(portfolioRequestDto.subColor());
        }
        if (portfolioRequestDto.backgroundColor() != null) {
            portfolio.setBackgroundColor(portfolioRequestDto.backgroundColor());
        }

        return PortfolioResponseDto.from(portfolioRepository.save(portfolio));
    }

    @Transactional
    public PortfolioUpdateTemplateResponseDto updateTemplate(Long portfolioId, PortfolioUpdateTemplateRequestDto requestDto) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolioId));

        Template template = templateRepository.findById(requestDto.getTemplateId())
                .orElseThrow(() -> new IllegalArgumentException("Template not found with ID: " + requestDto.getTemplateId()));

        // @Transactional의 DirtyChecking으로 save 없이 수정 사항 DB에 반영
        portfolio.updatePortfolioTemplate(template);
        return PortfolioUpdateTemplateResponseDto.builder()
                .portfolioId(portfolio.getPortfolioId())
                .templateId(portfolio.getTemplate().getTemplateId())
                .updatedAt(portfolio.getUpdatedAt())
                .build();
    }

    @Transactional
    public void deletePortfolio(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolioId));

        // CascadeType.ALL -> 영속성 전이 설정으로 연관된 domain, survey 삭제 로직 X!

        // Portfolio 삭제
        portfolioRepository.delete(portfolio);
    }
}
