package com.example.PING.portfolio.repository;

import com.example.PING.domain.entity.Domain;
import com.example.PING.portfolio.entity.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Portfolio findByDomain(Domain domain);
    // 추가적인 쿼리 메서드를 정의할 수 있습니다.
//    List<Portfolio> findByUserId(Long userId);
    Page<Portfolio> findAllByOrderByCreatedAtDesc(Pageable pageable); // 최신순 정렬
    Page<Portfolio> findAllByOrderByLikesDesc(Pageable pageable);    // 좋아요순 정렬
}
