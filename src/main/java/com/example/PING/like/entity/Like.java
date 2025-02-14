package com.example.PING.like.entity;

import com.example.PING.portfolio.entity.Portfolio;
import com.example.PING.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @OneToMany(mappedBy = "like", cascade = CascadeType.ALL)
    @JoinColumn(name = "portfolio_id")
    private List<Portfolio> portfolios = new ArrayList<>();

    @OneToMany(mappedBy = "like", cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<User> users = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
