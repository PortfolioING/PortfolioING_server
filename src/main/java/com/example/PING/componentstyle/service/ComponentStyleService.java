package com.example.PING.componentstyle.service;

import com.example.PING.componentstyle.dto.ComponentStyleDto;
import com.example.PING.componentstyle.dto.ComponentStyleResponse;
import com.example.PING.componentstyle.entity.ComponentStyle;
import com.example.PING.componentstyle.repository.ComponentStyleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;

@Service
public class ComponentStyleService {

    private final ComponentStyleRepository componentStyleRepository;

    @Autowired
    public ComponentStyleService(ComponentStyleRepository componentStyleRepository) {
        this.componentStyleRepository = componentStyleRepository;
    }

    @Transactional
    public ComponentStyleResponse createComponentStyle(ComponentStyleDto requestDto) {

        if (requestDto.textColor() != null)
            validateColorCode(requestDto.textColor());
        if (requestDto.backgroundColor() != null)
            validateColorCode(requestDto.backgroundColor());

        ComponentStyle componentStyle = new ComponentStyle();

        componentStyle.setBold(requestDto.bold() != null ? requestDto.bold() : Boolean.FALSE); // 디폴트 값으로 false 설정
        componentStyle.setItalic(requestDto.italic() != null ? requestDto.italic() : Boolean.FALSE); // 디폴트 값으로 false 설정
        componentStyle.setUnderscore(requestDto.underscore() != null ? requestDto.underscore() : Boolean.FALSE); // 디폴트 값으로 false 설정
        componentStyle.setStrikethrough(requestDto.strikethrough() != null ? requestDto.strikethrough() : Boolean.FALSE); // 디폴트 값으로 false 설정
        componentStyle.setSize(requestDto.size());
        componentStyle.setTextColor(requestDto.textColor() != null ? requestDto.textColor() : "#000000"); // 디폴트 값으로 검정색 설정
        componentStyle.setBackgroundColor(requestDto.backgroundColor());

        componentStyle = componentStyleRepository.save(componentStyle);

        return ComponentStyleResponse.from(componentStyle);

    }

    // 색상 코드 유효성 검증 메소드
    private void validateColorCode(String colorCode) {
        if (!colorCode.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")) {
            throw new IllegalArgumentException("Invalid color code: " + colorCode);
        }
    }
}
