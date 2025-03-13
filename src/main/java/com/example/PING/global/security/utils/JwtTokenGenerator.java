package com.example.PING.global.security.utils;

import com.example.PING.auth.dto.response.TokenSetResponse;
import com.example.PING.auth.dto.response.TemporaryTokenResponse;
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

    /**
     * 액세스 토큰 & 리프레시 토큰 생성
     */
    public TokenSetResponse generateTokenPair(User user) { // 정상 토큰 발급의 경우
        String accessToken = createAccessToken(user);
        String refreshToken = createRefreshToken(user);
        String temporaryToken = null;
        return TokenSetResponse.of(accessToken, refreshToken, temporaryToken);
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

    /**
     * 임시 토큰 생성
     */
    public TokenSetResponse generateTemporaryToken(User user) {
        String accessToken = null;
        String refreshToken = null;
        String temporaryToken = createTemporaryToken(user);
        return TokenSetResponse.of(accessToken, refreshToken, temporaryToken);
    }

    private String createTemporaryToken(User user) {
        return jwtUtil.generateTemporaryToken(user);
    }
}
