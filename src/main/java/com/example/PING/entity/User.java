package com.example.PING.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @OneToMany(mappedBy = "user")
    private List<Portfolio> portfolios = new ArrayList<>();

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    private String profilePic;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @Getter private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Getter private LocalDateTime updatedAt;

    @Builder
    public User(String password, String name, String email, String nickname, String profilePic) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.profilePic = profilePic;
    }
    public static User objectToUser(Object user) {
        if (user == null) new IllegalArgumentException("로그인된 User가 존재하지 않습니다.");
        return (User) user;
    }

    public List<Long> getPortfolioIds() {
        return portfolios.stream()
                .map(Portfolio::getId)
                .toList();
    }

}
