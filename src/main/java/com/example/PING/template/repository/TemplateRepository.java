package com.example.PING.template.repository;

import com.example.PING.template.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    // 추가적인 쿼리 메서드를 정의할 수 있습니다.
//    Template findByName(String name);
}
