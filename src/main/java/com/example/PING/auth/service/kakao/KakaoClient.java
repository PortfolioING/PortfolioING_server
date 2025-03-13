package com.example.PING.auth.service.kakao;

import com.example.PING.auth.dto.response.auth.KakaoAuthResponse;
import com.example.PING.auth.dto.response.auth.OAuthUserInfoResponse;
import com.example.PING.auth.service.OAuthProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoClient {

    private final RestClient restClient;
    public static final String KAKAO_USER_ME_URL = "https://kapi.kakao.com/v2/user/me";
    public static final String TOKEN_PREFIX = "Bearer ";

    public OAuthUserInfoResponse getOauthUserInfo(String token) {
        KakaoAuthResponse kakaoAuthResponse =
                restClient
                        .get()
                        .uri(KAKAO_USER_ME_URL)
                        .header("Authorization", TOKEN_PREFIX + token)
                        .exchange(
                                (request, response) -> {
                                    HttpStatusCode statusCode = response.getStatusCode();
                                    log.info("Received response with status: {}", statusCode);

                                    if (!response.getStatusCode().is2xxSuccessful()) {
                                        log.error("Kakao API error. Status: {}, Response: {}",
                                                statusCode, response.bodyTo(String.class));
                                        throw new AccessDeniedException("카카오 AT 인증이 실패했습니다.");
                                    }
                                    return Objects.requireNonNull(
                                            response.bodyTo(KakaoAuthResponse.class));
                                });

        return new OAuthUserInfoResponse(
                kakaoAuthResponse.id().toString(),
                kakaoAuthResponse.kakaoAccount().email(),
                kakaoAuthResponse.properties().nickname(),
                OAuthProvider.KAKAO
        );
    }
}