package com.example.PING.service;

import com.example.PING.dto.request.StyleRequestDto;
import com.example.PING.entity.Style;
import com.example.PING.repository.StyleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StyleService {

    private final StyleRepository styleRepository;

    @Autowired
    public StyleService(StyleRepository styleRepository) {
        this.styleRepository = styleRepository;
    }

    @Transactional
    public Style createEmptyStyle() {
        // 빌더를 사용하여 빈 스타일 객체 생성
        Style emptyStyle = Style.builder()
                .mainColor(null)
                .subColor(null)
                .backgroundColor(null)
                .build();

        // 생성된 빈 스타일을 데이터베이스에 저장
        return styleRepository.save(emptyStyle);
    }

    @Transactional
    public Style createStyle(StyleRequestDto requestDto) {
        Style style = Style.builder()
                .mainColor(requestDto.mainColor())
                .subColor(requestDto.subColor())
                .backgroundColor(requestDto.backgroundColor())
                .build();
        return styleRepository.save(style);
    }

    @Transactional
    public Style updateStyle(Long styleId, StyleRequestDto requestDto) {
        Style style = styleRepository.findById(styleId)
                .orElseThrow(() -> new IllegalArgumentException("Style not found with ID: " + styleId));

        // 직접 필드를 업데이트
        if (requestDto.mainColor() != null) {
            style.setMainColor(requestDto.mainColor());
        }
        if (requestDto.subColor() != null) {
            style.setSubColor(requestDto.subColor());
        }
        if (requestDto.backgroundColor() != null) {
            style.setBackgroundColor(requestDto.backgroundColor());
        }

        // 데이터베이스에 기존 객체 업데이트
        return styleRepository.save(style);
    }
}
