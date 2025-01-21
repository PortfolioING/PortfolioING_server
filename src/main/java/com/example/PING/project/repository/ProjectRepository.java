package com.example.PING.project.repository;

import com.example.PING.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // 추가적인 쿼리 메서드를 정의할 수 있습니다.
//    List<Project> findByPortfolioId(Long portfolioId);
}
