package com.example.PING.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String accessTokenSecret,
        String refreshTokenSecret,
        String temporaryTokenSecret,
        Long accessTokenExpirationTime,
        Long refreshTokenExpirationTime,
        Long temporaryTokenExpirationTime,
        String issuer
) {

    public Long accessTokenExpirationMilliTime() {
        return accessTokenExpirationTime * 1000;
    }

    public Long refreshTokenExpirationMilliTime() {
        return refreshTokenExpirationTime * 1000;
    }

    public Long temporaryTokenExpirationMilliTime() {return temporaryTokenExpirationTime * 1000;}
}
