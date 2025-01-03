package com.example.PING.repository;

import com.example.PING.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // 추가적인 쿼리 메서드를 정의할 수 있습니다.
//    List<Project> findByPortfolioId(Long portfolioId);
}
