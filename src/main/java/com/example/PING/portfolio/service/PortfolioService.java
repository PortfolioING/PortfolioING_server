package com.example.PING.portfolio.service;

import com.example.PING.component.entity.Component;
import com.example.PING.component.repository.ComponentRepository;
import com.example.PING.domain.repository.DomainRepository;
import com.example.PING.portfolio.dto.request.PortfolioCreateRequest;
import com.example.PING.portfolio.dto.request.PortfolioRequest;
import com.example.PING.portfolio.dto.request.PortfolioUpdateTemplateRequest;
import com.example.PING.portfolio.dto.response.PortfolioCreateResponse;
import com.example.PING.portfolio.dto.response.PortfolioUpdateTemplateResponse;
import com.example.PING.portfolio.dto.response.PortfolioResponse;
import com.example.PING.portfolio.entity.Portfolio;
import com.example.PING.portfolio.repository.PortfolioRepository;
import com.example.PING.template.entity.Template;
import com.example.PING.template.repository.TemplateRepository;
import com.example.PING.user.entity.User;
import com.example.PING.portfolio.dto.response.PortfolioDemoResponse;
import com.example.PING.user.repository.UserRepository;
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
    private final ComponentRepository componentRepository;
    private final DomainRepository domainRepository;
//    private final HttpSession httpSession;


    @Transactional
    public PortfolioCreateResponse createPortfolio(PortfolioCreateRequest requestDto) {

        // User, Survey, Template 엔티티 조회
        User user = userRepository.findById(requestDto.user_id())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + requestDto.user_id()));
        Component component = componentRepository.findById(requestDto.component_id())
                .orElseThrow(() -> new IllegalArgumentException("Survey not found with ID: " + requestDto.component_id()));
        Template template = templateRepository.findById(requestDto.template_id())
                .orElseThrow(() -> new IllegalArgumentException("Template not found with ID: " + requestDto.template_id()));

        // Portfolio 생성 및 설정
        Portfolio portfolio = Portfolio.builder()
                .user(user)
                .template(template)
                .component(component)
                .build();

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        return new PortfolioCreateResponse(
                savedPortfolio.getPortfolioId()
        );
    }

    // 특정 사용자의 포트폴리오 리스트 조회
    @Transactional(readOnly = true)
    public PortfolioDemoResponse getPortfolioDemo(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolioId));
        return PortfolioDemoResponse.from(portfolio);
    }

//    // 특정 사용자의 포트폴리오 리스트 조회
//    @Transactional(readOnly = true)
//    public UserPortfoliosResponse getUserPortfolios(Long userId) {
//
//        List<PortfolioResponse> portfolios = portfolioRepository.findAll().stream()
//                .filter(portfolio -> portfolio.getUser().getUserId().equals(userId))
//                .map(PortfolioResponse::from)
//                .collect(Collectors.toList());
//
//        return new UserPortfoliosResponse(userId, portfolios);
//    }


    @Transactional(readOnly = true)
    public PortfolioResponse getPortfolioById(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolioId));
        return PortfolioResponse.from(portfolio);
    }

    @Transactional
    public PortfolioResponse updatePortfolio(Long portfolioId, PortfolioRequest portfolioRequest) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolioId));

        // 포트폴리오 내용 필드 업데이트
//        if (portfolioRequest.title() != null) {
//            portfolio.setTitle(portfolioRequest.title());
//        }
//        if (portfolioRequest.description() != null) {
//            portfolio.setDescription(portfolioRequest.description());
//        }
//        if (portfolioRequestDto.mainColor() != null) {
//            portfolio.setMainColor(portfolioRequestDto.mainColor());
//        }
//        if (portfolioRequestDto.subColor() != null) {
//            portfolio.setSubColor(portfolioRequestDto.subColor());
//        }
//        if (portfolioRequestDto.backgroundColor() != null) {
//            portfolio.setBackgroundColor(portfolioRequestDto.backgroundColor());
//        }
//        수정 필요!!!!!!!!

        return PortfolioResponse.from(portfolioRepository.save(portfolio));
    }

    @Transactional
    public PortfolioUpdateTemplateResponse updateTemplate(Long portfolioId, PortfolioUpdateTemplateRequest requestDto) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolioId));

        Template template = templateRepository.findById(requestDto.templateId())
                .orElseThrow(() -> new IllegalArgumentException("Template not found with ID: " + requestDto.templateId()));

        // @Transactional의 DirtyChecking으로 save 없이 수정 사항 DB에 반영
        portfolio.updatePortfolioTemplate(template);
        return new PortfolioUpdateTemplateResponse(
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
