package com.example.PING;
import com.example.PING.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestProjectRepository extends JpaRepository<Project, Long> {
}
