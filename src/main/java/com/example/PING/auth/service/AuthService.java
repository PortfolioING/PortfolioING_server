package com.example.PING.auth.service;

import com.example.PING.auth.dto.response.LoginResponse;
import com.example.PING.auth.dto.response.TokenPairResponse;
import com.example.PING.auth.dto.response.auth.OAuthUserInfoResponse;
import com.example.PING.auth.service.kakao.KakaoClient;
import com.example.PING.global.security.utils.JwtTokenGenerator;
import com.example.PING.user.entity.OauthInfo;
import com.example.PING.user.entity.User;
import com.example.PING.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoClient kakaoClient;
    private final UserService userService;
    private final JwtTokenGenerator jwtTokenGenerator;

    @Transactional
    public LoginResponse socialLogin(String socialAccessToken, String provider) {
        OAuthUserInfoResponse oauthUserInfoResponse = getOauthUserInfo(socialAccessToken, OAuthProvider.from(provider));
        OauthInfo oauthInfo = oauthUserInfoResponse.toEntity();

        User user = userService.getUserByOAuthInfo(oauthInfo);
        // Todo 만약 소셜로그인으로 새로이 회원가입을 한 사람이라면, 여기서 nickname 필드가 null로 되어 있음.
        // Todo 여기서 닉네임을 받아서 처리를 하는 게 가장 좋지 않을까?

        TokenPairResponse tokenPairResponse = jwtTokenGenerator.generateTokenPair(user);
        return LoginResponse.of(user, tokenPairResponse); // Todo 닉네임이 null이어도 일단 처리가 가능할지 확인할 것
    }

    private OAuthUserInfoResponse getOauthUserInfo(String socialAccessToken, OAuthProvider provider) {
        return switch (provider) {
            case APPLE -> null; // 나중에 다른 것도 하면 이런 식으로~^^
            case KAKAO -> kakaoClient.getOauthUserInfo(socialAccessToken);
        };
    }
}
