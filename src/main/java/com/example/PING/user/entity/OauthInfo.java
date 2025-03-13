package com.example.PING.user.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthInfo {

    public String oauthId;
    private String oauthProvider;
    private String oauthEmail;
    private String oauthName;

    @Builder
    public OauthInfo(String oauthId, String oauthProvider, String oauthEmail, String oauthName) {
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
        this.oauthEmail = oauthEmail;
        this.oauthName = oauthName;
    }
}
