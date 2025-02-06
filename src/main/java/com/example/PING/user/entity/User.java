package com.example.PING.user.entity;
import com.example.PING.portfolio.entity.Portfolio;
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
    private List<Portfolio> portfolios = new ArrayList<>();

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

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
    public User(String password, String name, String email, String nickname, String userIcon, OauthInfo oauthInfo) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.userIcon = userIcon;
        this.oauthInfo = oauthInfo;
    }

    public static User createDefaultUser(OauthInfo oauthInfo) { // 소셜로그인으로 인해 생성되는 유저
        return User.builder()
                .nickname(null) // 사용자가 입력할 닉네임 (초기에는 null)
                // Todo 닉네임 null로 해놓는 게 가능한 건지 확인해야 함. 닉네임 설정 로직 어떻게 할지
                .userIcon("default") // 기본 프로필 아이콘 (임시)
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

    public void changeName(String newName){
        this.name = newName;
    }

    public void changeNickName(String newNickname){
        this.nickname = newNickname;
    }

    public void changePassword(String newPassword){
        this.password = newPassword;
    }

    public void changeUserIcon(String newUserIcon){
        this.userIcon = newUserIcon;
    }

}
