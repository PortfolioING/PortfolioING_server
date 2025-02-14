package com.example.PING.scrap.service;

import com.example.PING.portfolio.entity.Portfolio;
import com.example.PING.portfolio.repository.PortfolioRepository;
import com.example.PING.scrap.dto.request.ScrapCreateRequest;
import com.example.PING.scrap.dto.response.ScrapResponse;
import com.example.PING.scrap.entity.Scrap;
import com.example.PING.scrap.repository.ScrapRepository;
import com.example.PING.user.entity.User;
import com.example.PING.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    @Transactional
    public ScrapResponse createScrap(ScrapCreateRequest request) {
        // 포트폴리오와 사용자 조회
        Portfolio portfolio = portfolioRepository.findById(request.portfolioId())
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + request.portfolioId()));
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.userId()));

        // Scrap 객체 생성 및 설정
        Scrap scrap = new Scrap();
        scrap.setPortfolio(portfolio);
        scrap.setUser(user);
        scrap.setCreatedAt(LocalDateTime.now());

        Scrap savedScrap = scrapRepository.save(scrap);
        return new ScrapResponse(savedScrap.getScrapId());
    }
}
