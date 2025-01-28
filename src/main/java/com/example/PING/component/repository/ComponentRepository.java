package com.example.PING.component.repository;

import com.example.PING.component.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {
    List<Component> findAllById(Iterable<Long> ids);
    // 추가적인 쿼리 메서드가 필요하면 여기에 정의
}