package com.example.PING.service;

import com.example.PING.dto.request.PortfolioCreateRequestDto;
import com.example.PING.dto.request.PortfolioRequestDto;
import com.example.PING.dto.request.PortfolioUpdateTemplateRequestDto;
import com.example.PING.dto.request.StyleRequestDto;
import com.example.PING.dto.response.*;
import com.example.PING.entity.*;
import com.example.PING.repository.*;
import com.example.PING.dto.response.PortfolioResponseDto;
import com.example.PING.entity.Portfolio;
import com.example.PING.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final StyleRepository styleRepository;
    private final StyleService styleService;

//    private final HttpSession httpSession;


    @Transactional
    public PortfolioCreateResponseDto createPortfolio(PortfolioCreateRequestDto requestDto) {

        // User, Survey 엔티티 조회
        User user = userRepository.findById(requestDto.user_id())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + requestDto.user_id()));
        Survey survey = surveyRepository.findById(requestDto.survey_id())
                .orElseThrow(() -> new IllegalArgumentException("Survey not found with ID: " + requestDto.survey_id()));

        // 빈 스타일 객체 생성
        Style style = styleService.createEmptyStyle();

        // Portfolio 생성 및 설정
        Portfolio portfolio = Portfolio.builder()
//                .user(loginUser)
                .user(user)
                .style(style)
                .survey(survey)
                .title(requestDto.title())
                .description(requestDto.description())
                .image(requestDto.image())
                .build();

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        // Survey 에도 Portfolio 설정
        survey.savePortfolioToSurvey(savedPortfolio); // 생성한 Portfolio 값을 survey에 저장
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

        // 수정 필요!!!!!!!!
        // Style 업데이트 처리
        if (portfolioRequestDto.style_id() != null) {
            styleService.updateStyle(portfolioRequestDto.style_id(), new StyleRequestDto(
                    portfolioRequestDto.style_id(),
                    portfolioRequestDto.mainColor(),
                    portfolioRequestDto.subColor(),
                    portfolioRequestDto.backgroundColor()));
        }

        return PortfolioResponseDto.from(portfolioRepository.save(portfolio));
    }

    @Transactional
    public PortfolioUpdateTemplateResponseDto updateTemplate(Long portfolioId, PortfolioUpdateTemplateRequestDto requestDto) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolioId));

        Template template = templateRepository.findById(requestDto.templateId())
                .orElseThrow(() -> new IllegalArgumentException("Template not found with ID: " + requestDto.templateId()));

        // @Transactional의 DirtyChecking으로 save 없이 수정 사항 DB에 반영
        portfolio.updatePortfolioTemplate(template);
        return new PortfolioUpdateTemplateResponseDto(
                portfolio.getPortfolioId(),
                portfolio.getTemplate().getTemplateId(),
                portfolio.getUpdatedAt()
        );
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
