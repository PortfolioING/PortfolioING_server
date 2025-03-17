package com.example.PING.auth.controller;

import com.example.PING.auth.dto.request.SocialSignUpRequest;
import com.example.PING.auth.dto.response.LoginResponse;
import com.example.PING.auth.dto.response.SocialLoginResponse;
import com.example.PING.auth.dto.response.SocialSignUpResponse;
import com.example.PING.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "토큰 발급(Access token/ Temporary token)",
            description = "소셜 로그인 후 Access token과 회원 정보를 발급합니다. 등록된 회원이 아닐 경우 Temporary token 발급"
    )
    @PostMapping("/oauth/social-login")
    public ResponseEntity<SocialLoginResponse> socialLogin(
            @RequestHeader("social_access_token") String accessToken,
            @RequestParam("provider")
            @Parameter(example = "kakao", description = "OAuth 제공자")
            String provider
    ) {
        LoginResponse response = authService.socialLogin(accessToken, provider);
        // Todo 정상토큰이면 로그인 완료
        HttpHeaders headers = new HttpHeaders();
        headers.set("AccessToken", response.accessToken());
        headers.set("RefreshToken", response.refreshToken());
        headers.set("TemporaryToken", response.temporaryToken());

        return new ResponseEntity<>(SocialLoginResponse.of(response), headers,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Temporary token을 통한 가입",
            description = "Temporary 토큰을 통해 임시 회원 확인 후, 정식 회원으로 등록합니다."
    )
    @PostMapping("/oauth/social-signUp") // 닉네임 받아서 유저 등록 및 회원 가입 완료
    public ResponseEntity<SocialSignUpResponse> socialSignUp(
            @RequestHeader("temporary_token") String temporaryToken,
            @RequestBody SocialSignUpRequest request // 바디에 닉네임 들어있도록
    ){
        // 받은 닉네임이랑 캐시메모리에 있는 정보 넣어서 정식 user 저장.
        // 정상토큰 발급
        LoginResponse response = authService.registerSocialSignUpUser(temporaryToken, request.nickname());

        HttpHeaders headers = new HttpHeaders();
        headers.set("AccessToken", response.accessToken());
        headers.set("RefreshToken", response.refreshToken());

        return new ResponseEntity<>(SocialSignUpResponse.of(response), headers, HttpStatus.OK);
    }
}