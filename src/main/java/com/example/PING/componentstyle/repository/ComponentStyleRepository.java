package com.example.PING.componentstyle.repository;

import com.example.PING.componentstyle.entity.ComponentStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentStyleRepository extends JpaRepository<ComponentStyle, Long> {
}
