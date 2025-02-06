package com.example.PING.security.utils;

import com.example.PING.auth.dto.response.TokenPairResponse;
import com.example.PING.auth.entity.RefreshToken;
import com.example.PING.auth.repository.RefreshTokenRepository;
import com.example.PING.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenGenerator {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenPairResponse generateTokenPair(User user) {
        String accessToken = createAccessToken(user);
        String refreshToken = createRefreshToken(user);
        return TokenPairResponse.of(accessToken, refreshToken);
    }

    private String createAccessToken(User user) {
        return jwtUtil.generateAccessToken(user);
    }

    private String createRefreshToken(User user) {
        String token = jwtUtil.generateRefreshToken(user);
        saveRefreshToken(user.getUserId(), token);
        return token;
    }

    private void saveRefreshToken(Long memberId, String refreshToken) {
        if(refreshTokenRepository.existsByMemberId(memberId)) {
            refreshTokenRepository.deleteByMemberId(memberId);
        }
        refreshTokenRepository.save(RefreshToken.builder()
                .memberId(memberId)
                .token(refreshToken)
                .build());
    }
}
