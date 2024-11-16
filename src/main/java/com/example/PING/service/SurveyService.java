package com.example.PING.service;

import com.example.PING.controller.SurveyController;
import com.example.PING.dto.request.ProjectRequestDto;
import com.example.PING.dto.request.SurveyRequestDto;
import com.example.PING.dto.response.ProjectIdResponseDto;
import com.example.PING.dto.response.ProjectResponseDto;
import com.example.PING.dto.response.SurveyResponseDto;
import com.example.PING.entity.Project;
import com.example.PING.entity.Survey;
import com.example.PING.error.ResourceNotFoundException;
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

    @Transactional
    public SurveyResponseDto createSurvey(SurveyRequestDto surveyRequest) { // 생성
        Survey survey = new Survey();

        // 포트폴리오 설정
        survey.setPortfolio(null);
        survey.setName(surveyRequest.getName());
        survey.setPr(surveyRequest.getPr());
        survey.setPic(surveyRequest.getPic());

        // 설문에 기존 프로젝트 리스트 추가
        List<Project> projects = surveyRequest.getProjects().stream()
                .map(projectRequest -> {
                    // 기존 프로젝트 조회
                    Project project = projectRepository.findById(projectRequest.getProject_id())
                            .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectRequest.getProject_id()));
                    project.setSurvey(survey); // 설문과 연결
                    return project;
                })
                .collect(Collectors.toList());

        survey.setCreatedAt(LocalDateTime.now());

        surveyRepository.save(survey); // 설문 저장

        // 프로젝트 저장
        projectRepository.saveAll(projects);

        // 응답 DTO 생성
        return convertToSurveyResponseDto(survey);
    }

    public SurveyResponseDto getSurvey(Long survey_id) { // 조회
        Survey survey = surveyRepository.findById(survey_id)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id " + survey_id));

        System.out.println(survey);
        return convertToSurveyResponseDto(survey);
    }

    @Transactional
    public SurveyResponseDto updateSurvey(Long survey_id, SurveyRequestDto surveyRequest) { // 수정
        Survey survey = surveyRepository.findById(survey_id)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id " + survey_id));

        // 기존 데이터 업데이트
        survey.setName(surveyRequest.getName());
        survey.setPr(surveyRequest.getPr());
        survey.setPic(surveyRequest.getPic());
        updateProjects(survey, surveyRequest.getProjects());
        survey.setUpdatedAt(LocalDateTime.now());

        // 설문조사 업데이트
        surveyRepository.save(survey);
        return convertToSurveyResponseDto(survey);
    }

    private void updateProjects(Survey survey, List<ProjectRequestDto> projectRequests) { // 수정
        List<Project> projects = survey.getProjects();
        for (ProjectRequestDto projectRequest : projectRequests) {
            // 기존 프로젝트 ID와 매칭되는 프로젝트를 찾아 업데이트
            for (Project project : projects) {
                if (project.getProjectId().equals(projectRequest.getProject_id())) {
                    project.setProjectName(projectRequest.getProject_name());
                    project.setImage(projectRequest.getImage());
                    project.setShortIntro(projectRequest.getShort_intro());
                    project.setLongIntro(projectRequest.getLong_intro());
                    project.setDate(projectRequest.getDate());
                    project.setTarget(projectRequest.getTarget());
                    project.setRole(projectRequest.getRole());
                    project.setProblem(projectRequest.getProblem());
                    project.setSolution(projectRequest.getSolution());
                    project.setFeedback(projectRequest.getFeedback());
                    break;
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public List<SurveyResponseDto> getAllSurveys() {
        return surveyRepository.findAll().stream()
                .map(this::convertToSurveyResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SurveyResponseDto getSurveyById(Long surveyId) {
        return convertToSurveyResponseDto(surveyRepository.findById(surveyId).orElse(null));
    }

    @Transactional
    public void deleteSurvey(Long surveyId) {
        surveyRepository.deleteById(surveyId);
    }

    private SurveyResponseDto convertToSurveyResponseDto(Survey survey) {
        return SurveyResponseDto.builder()
                .surveyId(survey.getSurveyId())
                .portfolioId(survey.getPortfolio() != null ? survey.getPortfolio().getPortfolioId() : null)
                .name(survey.getName())
                .pr(survey.getPr())
                .pic(survey.getPic())
                .projects(
                        survey.getProjects() != null
                                ? survey.getProjects().stream()
                                .map(project -> new ProjectIdResponseDto(project.getProjectId()))
                                .collect(Collectors.toList())
                                : List.of()
                )
                .createdAt(survey.getCreatedAt())
                .updatedAt(survey.getUpdatedAt())
                .build();
    }

}
