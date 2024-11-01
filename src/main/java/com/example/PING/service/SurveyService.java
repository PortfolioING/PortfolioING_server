package com.example.PING.service;

import com.example.PING.controller.SurveyController;
import com.example.PING.dto.request.SurveyRequestDto;
import com.example.PING.dto.response.ProjectResponseDto;
import com.example.PING.dto.response.SurveyResponseDto;
import com.example.PING.entity.Portfolio;
import com.example.PING.entity.Project;
import com.example.PING.entity.Survey;
import com.example.PING.repository.PortfolioRepository;
import com.example.PING.repository.ProjectRepository;
import com.example.PING.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final ProjectRepository projectRepository;
    private final SurveyController surveyController;

    @Transactional
    public SurveyResponseDto createSurvey(SurveyRequestDto surveyRequest) {
        Survey survey = new Survey();

        // 포트폴리오 설정
        survey.setPortfolio(null);
        survey.setName(surveyRequest.getName());
        survey.setPR(surveyRequest.getPR());
        survey.setPic(surveyRequest.getPic());

        // 설문에 프로젝트 리스트 추가
        List<Project> projects = surveyRequest.getProjects().stream()
                .map(projectRequest -> {
                    Project project = new Project();
                    project.setProjectName(projectRequest.getProjectName());
                    project.setImage(projectRequest.getImage());
                    project.setShortIntro(projectRequest.getShortIntro());
                    project.setLongIntro(projectRequest.getLongIntro());
                    project.setDate(LocalDateTime.parse(projectRequest.getDate()));
                    project.setTarget(projectRequest.getTarget());
                    project.setRole(projectRequest.getRole());
                    project.setProblem(projectRequest.getProblem());
                    project.setSolution(projectRequest.getSolution());
                    project.setFeedback(projectRequest.getFeedback());
                    project.setSurvey(survey); // 설문과 연결
                    return project;
                })
                .collect(Collectors.toList());

        surveyRepository.save(survey); // 설문 저장
        survey.setCreatedAt(LocalDateTime.now());
        // 프로젝트 저장
        projectRepository.saveAll(projects);

        // 응답 DTO 생성
        return convertToResponseDto(survey);
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
        SurveyResponseDto dto = new SurveyResponseDto();
        dto.setSurveyId(survey.getSurveyId());
        dto.setPortfolioId(null);
        dto.setName(survey.getName());
        dto.setPR(survey.getPR());
        dto.setPic(survey.getPic());
        dto.setProjects(convertToProjectResponseDto(survey.getProjects()));
        dto.setCreatedAt(survey.getCreatedAt());
        dto.setUpdatedAt(LocalDateTime.now());
        return dto;
    }

    public static List<ProjectResponseDto> convertToProjectResponseDto(List<Project> projects) {
        return projects.stream().map(project -> {
            ProjectResponseDto responseDto = new ProjectResponseDto();
            responseDto.setProjectId(project.getProjectId());
            responseDto.setProjectName(project.getProjectName());
            responseDto.setImage(project.getImage());
            responseDto.setShortIntro(project.getShortIntro());
            responseDto.setLongIntro(project.getLongIntro());
            responseDto.setDate(String.valueOf(project.getDate()));
            responseDto.setTarget(project.getTarget());
            responseDto.setRole(project.getRole());
            responseDto.setProblem(project.getProblem());
            responseDto.setSolution(project.getSolution());
            responseDto.setFeedback(project.getFeedback());
            return responseDto;
        }).collect(Collectors.toList());
    }
}
