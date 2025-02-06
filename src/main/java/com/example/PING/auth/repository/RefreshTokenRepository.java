package com.example.PING.auth.repository;

import com.example.PING.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    boolean existsByMemberId(Long memberId);

    void deleteByMemberId(Long memberId);
}
