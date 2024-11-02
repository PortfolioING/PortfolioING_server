package com.example.PING.service;

import com.example.PING.dto.SurveyRequestDto;
import com.example.PING.dto.SurveyResponseDto;
import com.example.PING.entity.Survey;
import com.example.PING.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;

    @Transactional
    public SurveyResponseDto createSurvey(SurveyRequestDto surveyRequestDto) {
        Survey survey = Survey.builder()
                .name(surveyRequestDto.getName())
                .PR(surveyRequestDto.getPR())
                .pic(surveyRequestDto.getPic())
                .build();
        // Portfolio 설정 추가 필요
        return convertToResponseDto(surveyRepository.save(survey));
    }

    @Transactional(readOnly = true)
    public List<SurveyResponseDto> getAllSurveys() {
        return surveyRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SurveyResponseDto getSurveyById(Long surveyId) {
        return convertToResponseDto(surveyRepository.findById(surveyId).orElse(null));
    }

    @Transactional
    public void deleteSurvey(Long surveyId) {
        surveyRepository.deleteById(surveyId);
    }

    private SurveyResponseDto convertToResponseDto(Survey survey) {
        return SurveyResponseDto.builder()
                .survey(survey)
                .build();
    }
}
