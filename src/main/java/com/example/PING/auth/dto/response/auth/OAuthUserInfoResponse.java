package com.example.PING.auth.dto.response.auth;

import com.example.PING.auth.service.OAuthProvider;
import com.example.PING.user.entity.OauthInfo;
import lombok.Builder;

@Builder
public record OAuthUserInfoResponse( // 받은 json 타입을 자바로 변환하기 위한 dto
// 카카오 말고 다른 거에서도 쓸 수 있게 하기 위해 공통 타입으로 변환하려고 이거 dto로 변환하는 거임
                                     String oauthId,
                                     String email,
                                     String name,
                                     OAuthProvider provider

) {
    public OauthInfo toEntity() {
        return OauthInfo.builder()
                .oauthId(this.oauthId())
                .oauthEmail(this.email())
                .oauthName(this.name())
                .oauthProvider(this.provider().name())
                .build();
    }
}
