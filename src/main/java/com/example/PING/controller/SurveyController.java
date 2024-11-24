package com.example.PING.controller;

import com.example.PING.dto.request.SurveyRequestDto;
import com.example.PING.dto.response.SurveyCreateResponseDto;
import com.example.PING.dto.response.SurveyResponseDto;
import com.example.PING.service.SurveyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping // 생성
    public ResponseEntity<SurveyCreateResponseDto> createSurvey(@RequestBody SurveyRequestDto surveyRequest) {
        SurveyCreateResponseDto createdSurvey = surveyService.createSurvey(surveyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSurvey);
    }

    @GetMapping("/{survey_id}") // 조회
    public ResponseEntity<SurveyResponseDto> getSurvey(@PathVariable Long survey_id) {
        SurveyResponseDto surveyResponse = surveyService.getSurvey(survey_id);
        return ResponseEntity.ok(surveyResponse);
    }

    @PutMapping("/{survey_id}") // 수정
    public ResponseEntity<SurveyResponseDto> updateSurvey(
            @PathVariable Long survey_id,
            @RequestBody SurveyRequestDto surveyRequest) {

        SurveyResponseDto updatedSurvey = surveyService.updateSurvey(survey_id, surveyRequest);
        return ResponseEntity.ok(updatedSurvey);
    }


}
