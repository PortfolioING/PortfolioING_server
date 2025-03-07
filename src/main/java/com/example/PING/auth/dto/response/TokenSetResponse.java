package com.example.PING.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenSetResponse(
        @Schema(description = "액세스 토큰", defaultValue = "accessToken") String accessToken,
        @Schema(description = "리프레시 토큰", defaultValue = "refreshToken") String refreshToken,
        @Schema(description = "임시 토큰", defaultValue = "temporaryToken") String temporaryToken) {

    public static TokenSetResponse of(String accessToken, String refreshToken, String temporaryToken) {
        return new TokenSetResponse(accessToken, refreshToken, temporaryToken);
    }
}
