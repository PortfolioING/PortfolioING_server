package com.example.PING.auth.service;

import com.example.PING.auth.dto.response.LoginResponse;
import com.example.PING.auth.dto.response.TokenSetResponse;
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
    private final TemporalUserCacheService temporalUserCacheService;

    @Transactional
    public LoginResponse socialLogin(String socialAccessToken, String provider) {
        OAuthUserInfoResponse oauthUserInfoResponse = getOauthUserInfo(socialAccessToken, OAuthProvider.from(provider));
        OauthInfo oauthInfo = oauthUserInfoResponse.toEntity();

        User user = userService.getUserByOAuthInfo(oauthInfo);
        // 만약 소셜로그인으로 새로이 회원가입을 한 사람이라면, 여기서 nickname 필드가 null로 되어 있음.

        if(user.getNickname() == null) { // 임시 토큰을 발급해야 하는 경우
            TokenSetResponse tokenSetResponse = jwtTokenGenerator.generateTemporaryToken(user);
            temporalUserCacheService.set(tokenSetResponse.temporaryToken(), user);
            return LoginResponse.of(user, tokenSetResponse);
        }

        TokenSetResponse tokenSetResponse = jwtTokenGenerator.generateTokenPair(user);
        return LoginResponse.of(user, tokenSetResponse);
    }

    public LoginResponse registerSocialSignUpUser(String tempToken, String nickname) {
        User tempUser = temporalUserCacheService.get(tempToken, User.class);
        if (tempUser == null) {
            throw new RuntimeException("인증 정보가 만료되었습니다.");
        }

        tempUser.setNickname(nickname); // 닉네임 설정
        User savedNewUser = userService.saveTempUser(tempUser); // DB에 새로운 회원으로 저장

        temporalUserCacheService.delete(tempToken); // 캐시에서 삭제
        // Todo 임시토큰 무효화해야 하나?
        return LoginResponse.of(savedNewUser, jwtTokenGenerator.generateTokenPair(savedNewUser));
    }

    private OAuthUserInfoResponse getOauthUserInfo(String socialAccessToken, OAuthProvider provider) {
        return switch (provider) {
            case APPLE -> null; // 나중에 다른 것도 하면 이런 식으로~^^
            case KAKAO -> kakaoClient.getOauthUserInfo(socialAccessToken);
        };
    }
}
