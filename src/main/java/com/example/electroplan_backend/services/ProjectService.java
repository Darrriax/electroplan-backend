package com.example.electroplan_backend.services;

import com.example.electroplan_backend.dto.entities.ProjectEntity;
import com.example.electroplan_backend.dto.requests.ProjectRequest;
import com.example.electroplan_backend.dto.responses.ProjectPageResponse;
import com.example.electroplan_backend.dto.responses.ProjectResponse;
import com.example.electroplan_backend.dto.responses.ProjectShortResponse;
import com.example.electroplan_backend.exceptions.project.ProjectNotFoundException;
import com.example.electroplan_backend.mappers.ProjectMapper;
import com.example.electroplan_backend.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectResponse createProject(ProjectRequest request, String userEmail) {
        ProjectEntity projectEntity = projectMapper.toProjectEntity(request);
        projectEntity.setUserEmail(userEmail);
        projectEntity.setCreatedAt(LocalDateTime.now());
        projectEntity.setUpdatedAt(LocalDateTime.now());
        ProjectEntity savedEntity = projectRepository.save(projectEntity);
        return projectMapper.toProjectResponse(savedEntity);
    }

    public ProjectResponse updateProject(ProjectRequest request, String userEmail) {
        ProjectEntity existingProject = projectRepository.findById(request.getId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + request.getId()));
        
        if (!existingProject.getUserEmail().equals(userEmail)) {
            throw new ProjectNotFoundException("Project not found with id: " + request.getId());
        }
        
        ProjectEntity projectEntity = projectMapper.toProjectEntity(request);
        projectEntity.setUserEmail(userEmail);
        projectEntity.setCreatedAt(existingProject.getCreatedAt());
        projectEntity.setUpdatedAt(LocalDateTime.now());
        
        ProjectEntity savedEntity = projectRepository.save(projectEntity);
        return projectMapper.toProjectResponse(savedEntity);
    }

    public ProjectResponse getProjectById(Long id, String userEmail) {
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));
        
        if (!projectEntity.getUserEmail().equals(userEmail)) {
            throw new ProjectNotFoundException("Project not found with id: " + id);
        }
        
        return projectMapper.toProjectResponse(projectEntity);
    }

    public ProjectPageResponse searchProjects(
            String titlePattern,
            String customerPattern,
            String userEmail
    ) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updated_at");
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);
        
        Page<ProjectEntity> projectPage = projectRepository.findProjectsWithFilters(
                userEmail,
                titlePattern,
                customerPattern,
                pageable
        );

        List<ProjectShortResponse> projectShortResponses = projectPage.getContent().stream()
                .map(projectMapper::toProjectShortResponse)
                .collect(Collectors.toList());

        ProjectPageResponse response = new ProjectPageResponse();
        response.setPageNumber(0);
        response.setTotalPages(1);
        response.setProjectShortResponses(projectShortResponses);

        return response;
    }

    public void deleteProject(Long id, String userEmail) {
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));
        
        if (!projectEntity.getUserEmail().equals(userEmail)) {
            throw new ProjectNotFoundException("Project not found with id: " + id);
        }
        
        projectRepository.deleteById(id);
    }
}