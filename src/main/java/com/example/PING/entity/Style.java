package com.example.PING.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
public class Style {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long styleId;

    @Column(name = "main_color", length = 7)
    private String mainColor;

    @Column(name = "sub_color", length = 7)
    private String subColor;

    @Column(name = "background_color", length = 7)
    private String backgroundColor;


//    @Builder
//    public Style() {
//        this.styleId = styleId;
//        this.mainColor = mainColor;
//        this.subColor = subColor;
//        this.backgroundColor = backgroundColor;
//    }
}
