package com.example.PING.survey.service;

import com.example.PING.survey.dto.request.SurveyRequest;
import com.example.PING.survey.dto.response.SurveyCreateResponse;
import com.example.PING.survey.dto.response.SurveyResponse;
import com.example.PING.project.entity.Project;
import com.example.PING.survey.entity.Survey;
import com.example.PING.error.exception.ResourceNotFoundException;
import com.example.PING.project.repository.ProjectRepository;
import com.example.PING.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public SurveyCreateResponse createSurvey(SurveyRequest surveyRequest) { // 생성
        Survey survey = new Survey();

        // 포트폴리오 설정
//        survey.setPortfolioNull(); // Survey를 생성하기 위해 Portfolio 값을 임시로 null로 초기화함.
        survey.setName(surveyRequest.name());
        survey.setIntroduce(surveyRequest.introduce());
        survey.setProfile(surveyRequest.profile());

        // 설문에 기존 프로젝트 리스트 추가
        List<Project> projects = surveyRequest.projectsId().stream()
                .map(projectId -> {
                    // 기존 프로젝트 조회
                    Project project = projectRepository.findById(projectId)
                            .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
                    project.setSurvey(survey); // 설문과 연결
                    return project;
                })
                .collect(Collectors.toList());

        surveyRepository.save(survey); // 설문 저장

        // 프로젝트 저장
        projectRepository.saveAll(projects);

        // 응답 DTO 생성
        return SurveyCreateResponse.from(survey);
    }

    public SurveyResponse getSurvey(Long survey_id) { // 조회
        Survey survey = surveyRepository.findById(survey_id)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id " + survey_id));

        System.out.println(survey);
        return SurveyResponse.from(survey);
    }

    @Transactional
    public SurveyResponse updateSurvey(Long survey_id, SurveyRequest surveyRequest) { // 수정
        Survey survey = surveyRepository.findById(survey_id)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id " + survey_id));

        // 기존 데이터 업데이트
        survey.setName(surveyRequest.name());
        survey.setIntroduce(surveyRequest.introduce());
        survey.setProfile(surveyRequest.profile());
        survey.setProjects(getProjectsById(surveyRequest.projectsId()));

        // 설문조사 업데이트
        surveyRepository.save(survey);
        return SurveyResponse.from(survey);
    }

    private List<Project> getProjectsById(List<Long> projectsId) {
        List<Project> projects = projectsId.stream()
                .map(projectId -> {
                    Project project = projectRepository.findById(projectId)
                            .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
                    return project;
                })
                .collect(Collectors.toList());
        return projects;
    }

    // Survey에서는 Project 자체에 대해 update를 수행할 필요 없음! 바뀐 projects에 대해서 mapping만 수정하면 됨!
//    private void updateProjects(Survey survey, List<Long> projectsId) { // 수정
//        List<Project> projects = survey.getProjects();
//        for (ProjectRequestDto projectRequest : projectRequests) {
//            // 기존 프로젝트 ID와 매칭되는 프로젝트를 찾아 업데이트
//            for (Project project : projects) {
//                if (project.getProjectId().equals(projectRequest.getProject_id())) {
//                    project.setProjectName(projectRequest.getProject_name());
//                    project.setImage(projectRequest.getImage());
//                    project.setShortIntro(projectRequest.getShort_intro());
//                    project.setLongIntro(projectRequest.getLong_intro());
//                    project.setDate(projectRequest.getDate());
//                    project.setTarget(projectRequest.getTarget());
//                    project.setRole(projectRequest.getRole());
//                    project.setProblem(projectRequest.getProblem());
//                    project.setSolution(projectRequest.getSolution());
//                    project.setFeedback(projectRequest.getFeedback());
//                    break;
//                }
//            }
//        }
//    }

    @Transactional(readOnly = true)
    public List<SurveyResponse> getAllSurveys() {
        return surveyRepository.findAll().stream()
                .map(SurveyResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SurveyResponse getSurveyById(Long surveyId) {
        return SurveyResponse.from(
                surveyRepository.findById(surveyId)
                        .orElseThrow(() -> new ResourceNotFoundException("Survey not found with ID: " + surveyId))
        );
    }

    @Transactional
    public void deleteSurvey(Long surveyId) {
        surveyRepository.deleteById(surveyId);
    }
}
