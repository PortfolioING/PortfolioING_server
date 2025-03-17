package com.example.PING.component.repository;

import com.example.PING.component.entity.Component;
import com.example.PING.portfolio.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {
    List<Component> findAllById(Iterable<Long> ids);

    // 특정 포트폴리오의 루트 컴포넌트 찾기
    Optional<Component> findByPortfolioAndParentComponentIsNull(Portfolio portfolio);

    // 특정 부모 컴포넌트의 자식 컴포넌트 리스트 조회
    @Query("SELECT c FROM Component c WHERE c.parentComponent = :parentComponent")
    List<Component> findChildrenByParentComponent(@Param("parentComponent") Component parentComponent);

}