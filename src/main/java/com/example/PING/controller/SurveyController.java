package com.example.PING.controller;

import com.example.PING.dto.request.SurveyRequest;
import com.example.PING.dto.response.SurveyCreateResponse;
import com.example.PING.dto.response.SurveyResponse;
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
    public ResponseEntity<SurveyCreateResponse> createSurvey(@RequestBody SurveyRequest surveyRequest) {
        SurveyCreateResponse createdSurvey = surveyService.createSurvey(surveyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSurvey);
    }

    @GetMapping("/{survey_id}") // 조회
    public ResponseEntity<SurveyResponse> getSurvey(@PathVariable Long survey_id) {
        SurveyResponse surveyResponse = surveyService.getSurvey(survey_id);
        return ResponseEntity.ok(surveyResponse);
    }

    @PutMapping("/{survey_id}") // 수정
    public ResponseEntity<SurveyResponse> updateSurvey(
            @PathVariable Long survey_id,
            @RequestBody SurveyRequest surveyRequest) {

        SurveyResponse updatedSurvey = surveyService.updateSurvey(survey_id, surveyRequest);
        return ResponseEntity.ok(updatedSurvey);
    }


}
