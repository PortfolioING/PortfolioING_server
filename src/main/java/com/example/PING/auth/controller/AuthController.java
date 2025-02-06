package com.example.PING.auth.controller;

import com.example.PING.auth.dto.response.LoginResponse;
import com.example.PING.auth.dto.response.SocialLoginResponse;
import com.example.PING.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
            summary = "소셜 로그인으로 회원가입",
            description = "소셜 로그인 후 Authorization 토큰과 회원 정보를 발급합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원가입 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SocialLoginResponse.class)
                            ),
                            headers = {
                                    @Header(name = "Authorization", description = "Access Token", schema = @Schema(type = "string")),
                                    @Header(name = "RefreshToken", description = "Refresh Token", schema = @Schema(type = "string"))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 오류",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PostMapping("/oauth/social-login")
    public ResponseEntity<SocialLoginResponse> socialLogin(
            @RequestHeader("social_access_token") String accessToken,
            @RequestParam("provider")
            @Parameter(example = "kakao", description = "OAuth 제공자")
            String provider
    ) {
        LoginResponse response = authService.socialLogin(accessToken, provider);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", response.accessToken());
        headers.set("RefreshToken", response.refreshToken());

        return new ResponseEntity<>(SocialLoginResponse.of(response), headers,
                HttpStatus.OK);
    }