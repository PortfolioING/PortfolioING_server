package com.example.PING.survey.repository;

import com.example.PING.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    // 추가적인 쿼리 메서드를 정의할 수 있습니다.
//    List<Survey> findByPortfolioId(Long portfolioId);
}
