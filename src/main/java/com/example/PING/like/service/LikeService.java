package com.example.PING.like.service;

import com.example.PING.like.dto.request.LikeCreateRequest;
import com.example.PING.like.dto.response.LikeResponse;
import com.example.PING.like.entity.Like;
import com.example.PING.like.repository.LikeRepository;
import com.example.PING.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    @Transactional
    public LikeResponse createLike(LikeCreateRequest request) {
        Like like = new Like();
        like.setPortfolios(request.portfolios());
        like.setUsers(request.users());
        like.setCreatedAt(LocalDateTime.now());

        Like savedLike = likeRepository.save(like);
        return new LikeResponse(savedLike.getLikeId());
    }

    // 추가적인 메서드 정의 가능
}
