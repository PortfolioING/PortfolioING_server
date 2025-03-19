package com.example.PING.global.security.utils;

import com.example.PING.global.properties.JwtProperties;
import com.example.PING.global.security.token.JwtAuthenticationToken;
import com.example.PING.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    public String generateAccessToken(User user) {
        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + jwtProperties.accessTokenExpirationMilliTime());
        if (user.getUserId() == null) {
            throw new IllegalStateException("User ID is null while generating JWT");
        }

        return Jwts.builder()
                .setIssuer(jwtProperties.issuer())
                .setSubject(user.getUserId().toString()) // 사용자 ID만 저장
                .claim("tokenType", "access") // 클레임으로 토큰 유형 저장
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(getAccessTokenKey())
                .compact();
    }

    public String generateRefreshToken(User user) {
        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + jwtProperties.refreshTokenExpirationMilliTime());

        return Jwts.builder()
                .setIssuer(jwtProperties.issuer())
                .setSubject(user.getUserId().toString()) // 사용자 ID만 저장
                .claim("tokenType", "refresh") // 클레임으로 토큰 유형 저장
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(getRefreshTokenKey())
                .compact();
    }

    /**
     * 임시 토큰 생성
     */
    public String generateTemporaryToken(User user) {
        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + jwtProperties.temporaryTokenExpirationMilliTime());

        return Jwts.builder()
                .setIssuer(jwtProperties.issuer())
                .setSubject(UUID.randomUUID().toString())  // 임시 고유 값 사용
                .claim("tokenType", "temporary") // 클레임으로 토큰 유형 저장
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(getTemporaryTokenKey())
                .compact();

    }

    public Claims getAccessTokenClaims(Authentication authentication) {
        return Jwts.parserBuilder()
                .requireIssuer(jwtProperties.issuer())
                .setSigningKey(getAccessTokenKey())
                .build()
                .parseClaimsJws(((JwtAuthenticationToken) authentication).getJsonWebToken())
                .getBody();
    }

    private Key getAccessTokenKey() {
        return Keys.hmacShaKeyFor(jwtProperties.accessTokenSecret().getBytes());
    }

    private Key getRefreshTokenKey() {
        return Keys.hmacShaKeyFor(jwtProperties.refreshTokenSecret().getBytes());
    }

    /**
     * 임시 토큰의 서명 키 (이거는 JwtProperties에서 관리해야 함)
     */
    private Key getTemporaryTokenKey() {
        return Keys.hmacShaKeyFor(jwtProperties.temporaryTokenSecret().getBytes());
    }
}