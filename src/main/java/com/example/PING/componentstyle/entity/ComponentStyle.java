package com.example.PING.componentstyle.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ComponentStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long componentStyleId;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean bold;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean italic;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean underscore;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean strikethrough;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false, columnDefinition = "varchar(7) default '#000000'")
    private String textColor;

    private String backgroundColor;

    @Builder
    public ComponentStyle(Boolean bold, Boolean italic, Boolean underscore, Boolean strikethrough,
                          Long size, String textColor, String backgroundColor) {
        this.bold = bold;
        this.italic = italic;
        this.underscore = underscore;
        this.strikethrough = strikethrough;
        this.size = size;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }

    // 색상 코드 유효성 검증 메소드
    private boolean validateColorCode (String colorCode) {
        return colorCode.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
    }

    // 색상 코드 설정 시 유효성 검사 로직
    public void setTextColor (String textColor) {
        if (validateColorCode(textColor))
            this.textColor = textColor;
        else
            throw new IllegalArgumentException("Invalid color code: " + textColor);
    }

    public void setBackgroundColor (String backgroundColor) {
        if (validateColorCode(backgroundColor))
            this.backgroundColor = backgroundColor;
        else
            throw new IllegalArgumentException("Invalid color code: " + backgroundColor);
    }
}
