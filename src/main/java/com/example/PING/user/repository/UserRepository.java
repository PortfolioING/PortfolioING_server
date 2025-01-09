package com.example.PING.user.repository;

import com.example.PING.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 추가적인 쿼리 메서드를 정의할 수 있습니다
    Optional<User> findByEmail(String email);
}

