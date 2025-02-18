package com.example.PING.like.service;

import com.example.PING.like.dto.request.LikeCreateRequest;
import com.example.PING.like.dto.response.LikeResponse;
import com.example.PING.like.entity.Like;
import com.example.PING.like.repository.LikeRepository;
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
public class LikeService {
    private final LikeRepository likeRepository;
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;


    @Transactional
    public LikeResponse createLike(LikeCreateRequest request) {
        // 포트폴리오와 사용자 조회
        Portfolio portfolio = portfolioRepository.findById(request.portfolioId())
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + request.portfolioId()));
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.userId()));

        Like like = new Like();
        like.setPortfolio(portfolio);
        like.setUser(user);
        like.setCreatedAt(LocalDateTime.now());

        Like savedLike = likeRepository.save(like);
        return new LikeResponse(savedLike.getLikeId());
    }

    // 추가적인 메서드 정의 가능
}
