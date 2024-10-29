package com.example.PING.repository;

import com.example.PING.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
    // 추가적인 쿼리 메서드를 정의할 수 있습니다.
//    Domain findByDomain(String domain);
}
