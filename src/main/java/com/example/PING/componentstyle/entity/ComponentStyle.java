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

}
