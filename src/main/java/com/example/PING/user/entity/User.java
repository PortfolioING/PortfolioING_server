package com.example.PING.user.entity;
import com.example.PING.likes.entity.Likes;
import com.example.PING.portfolio.entity.Portfolio;
import com.example.PING.scrap.entity.Scrap;
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

    @Embedded //소셜로그인
    private OauthInfo oauthInfo;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Portfolio> portfolios = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Scrap> scraps = new ArrayList<>();

    @Column(nullable = false)
    private String nickname;

    private String userIcon;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @Getter private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Getter private LocalDateTime updatedAt;

    @Builder // Todo 소셜로그인
    public User(String nickname, String userIcon, OauthInfo oauthInfo) {
        this.nickname = nickname;
        this.userIcon = userIcon;
        this.oauthInfo = oauthInfo;
    }

    public static User createTemporalUser(OauthInfo oauthInfo) { // 소셜로그인으로 인해 생성되는 유저
        return User.builder()
                .nickname(null) // 사용자가 입력할 닉네임 (초기에는 null)
                .userIcon("default") // Todo 기본 프로필 아이콘 (임시)
                .oauthInfo(oauthInfo)
                .build();
    }

    public static User objectToUser(Object user) {
        if (user == null) throw new IllegalArgumentException("로그인된 User가 존재하지 않습니다.");
        return (User) user;
    }

    public List<Long> getPortfolioIds() {
        return portfolios.stream()
                .map(Portfolio::getId)
                .toList();
    }

    public void changeNickName(String newNickname){
        this.nickname = newNickname;
    }

    public void changeUserIcon(String newUserIcon){
        this.userIcon = newUserIcon;
    }

}
