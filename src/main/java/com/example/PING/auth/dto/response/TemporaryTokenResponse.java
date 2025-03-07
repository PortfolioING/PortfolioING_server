package com.example.PING.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TemporaryTokenResponse(
        @Schema(description = "임시 토큰", defaultValue = "temporaryToken") String temporaryToken) {

    public static TemporaryTokenResponse of(String temporaryToken) {
        return new TemporaryTokenResponse(temporaryToken);
    }
}
