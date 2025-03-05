package com.example.PING.likes.service;

import com.example.PING.likes.dto.request.LikesCreateRequest;
import com.example.PING.likes.dto.response.LikesResponse;
import com.example.PING.likes.entity.Likes;
import com.example.PING.likes.repository.LikesRepository;
import com.example.PING.portfolio.entity.Portfolio;
import com.example.PING.portfolio.repository.PortfolioRepository;
import com.example.PING.user.entity.User;
import com.example.PING.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;


    @Transactional
    public LikesResponse createLike(LikesCreateRequest request) {
        // 포트폴리오와 사용자 조회
        Portfolio portfolio = portfolioRepository.findById(request.portfolioId())
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + request.portfolioId()));
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.userId()));

        Likes likes = new Likes();
        likes.setPortfolio(portfolio);
        likes.setUser(user);
        likes.setCreatedAt(LocalDateTime.now());

        Likes savedLikes = likesRepository.save(likes);
        return new LikesResponse(savedLikes.getLikesId());
    }

    // 추가적인 메서드 정의 가능
}
