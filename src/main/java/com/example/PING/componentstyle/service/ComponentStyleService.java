package com.example.PING.componentstyle.service;

import com.example.PING.componentstyle.dto.request.ComponentStyleRequest;
import com.example.PING.componentstyle.dto.response.ComponentStyleResponse;
import com.example.PING.componentstyle.entity.ComponentStyle;
import com.example.PING.componentstyle.repository.ComponentStyleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComponentStyleService {

    private final ComponentStyleRepository componentStyleRepository;

    @Autowired
    public ComponentStyleService(ComponentStyleRepository componentStyleRepository) {
        this.componentStyleRepository = componentStyleRepository;
    }

    // 색상 코드 유효성 검증 메소드
    private void validateColorCode(String colorCode) {
        if (!colorCode.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")) {
            throw new IllegalArgumentException("Invalid color code: " + colorCode);
        }
    }

    @Transactional
    public ComponentStyleResponse createComponentStyle(ComponentStyleRequest requestDto) {

        if (requestDto.textColor() != null)
            validateColorCode(requestDto.textColor());
        if (requestDto.backgroundColor() != null)
            validateColorCode(requestDto.backgroundColor());

        ComponentStyle componentStyle = new ComponentStyle();

        componentStyle.setBold(requestDto.bold() != null ? requestDto.bold() : Boolean.FALSE); // 디폴트 값으로 false 설정
        componentStyle.setItalic(requestDto.italic() != null ? requestDto.italic() : Boolean.FALSE); // 디폴트 값으로 false 설정
        componentStyle.setUnderscore(requestDto.underscore() != null ? requestDto.underscore() : Boolean.FALSE); // 디폴트 값으로 false 설정
        componentStyle.setStrikethrough(requestDto.strikethrough() != null ? requestDto.strikethrough() : Boolean.FALSE); // 디폴트 값으로 false 설정
        componentStyle.setSize(requestDto.size() != null ? requestDto.size() : Long.valueOf(10)); //디폴트 값으로 10pt 설정
        componentStyle.setTextColor(requestDto.textColor() != null ? requestDto.textColor() : "#000000"); // 디폴트 값으로 검정색 설정
        componentStyle.setBackgroundColor(requestDto.backgroundColor());

        componentStyle = componentStyleRepository.save(componentStyle);

        return ComponentStyleResponse.from(componentStyle);
    }

    @Transactional
    public ComponentStyleResponse updateComponentStyle(Long id, ComponentStyleRequest requestDto) {

        ComponentStyle componentStyle = componentStyleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Component style not found with id: " + id));

        if (requestDto.textColor() != null)
            validateColorCode(requestDto.textColor());
        if (requestDto.backgroundColor() != null)
            validateColorCode(requestDto.backgroundColor());

        componentStyle.setBold(requestDto.bold() != null ? requestDto.bold() : Boolean.FALSE); // 디폴트 값으로 false 설정
        componentStyle.setItalic(requestDto.italic() != null ? requestDto.italic() : Boolean.FALSE); // 디폴트 값으로 false 설정
        componentStyle.setUnderscore(requestDto.underscore() != null ? requestDto.underscore() : Boolean.FALSE); // 디폴트 값으로 false 설정
        componentStyle.setStrikethrough(requestDto.strikethrough() != null ? requestDto.strikethrough() : Boolean.FALSE); // 디폴트 값으로 false 설정
        componentStyle.setSize(requestDto.size() != null ? requestDto.size() : Long.valueOf(10));
        componentStyle.setTextColor(requestDto.textColor() != null ? requestDto.textColor() : "#000000"); // 디폴트 값으로 검정색 설정
        componentStyle.setBackgroundColor(requestDto.backgroundColor());

        componentStyle = componentStyleRepository.save(componentStyle);

        return ComponentStyleResponse.from(componentStyle);
    }
}
