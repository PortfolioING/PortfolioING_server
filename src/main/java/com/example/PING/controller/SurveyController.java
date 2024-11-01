package com.example.PING.controller;

import com.example.PING.dto.request.SurveyRequestDto;
import com.example.PING.dto.response.SurveyResponseDto;
import com.example.PING.service.SurveyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping
    public ResponseEntity<SurveyResponseDto> createSurvey(@RequestBody SurveyRequestDto surveyRequest) {
        SurveyResponseDto createdSurvey = surveyService.createSurvey(surveyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSurvey);
    }
}
