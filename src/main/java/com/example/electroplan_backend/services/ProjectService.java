package com.example.electroplan_backend.services;

import com.example.electroplan_backend.dto.entities.ProjectEntity;
import com.example.electroplan_backend.dto.responses.ProjectImageResponse;
import com.example.electroplan_backend.dto.responses.ProjectPageResponse;
import com.example.electroplan_backend.dto.responses.ProjectResponse;
import com.example.electroplan_backend.dto.responses.ProjectShortResponse;
import com.example.electroplan_backend.exceptions.project.ProjectNotFoundException;
import com.example.electroplan_backend.mappers.ProjectMapper;
import com.example.electroplan_backend.mappers.UserMapperImpl;
import com.example.electroplan_backend.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {

    private ProjectRepository projectRepository;
    private ProjectMapper projectMapper;
    private UserService userService;
    private ProjectImageService projectImageService;
    private UserMapperImpl userMapperImpl;

    public ProjectPageResponse searchProjectsByUser(
            Long userId,
            String titlePattern,
            String customerPattern,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt"));

        Page<ProjectEntity> projectPage = projectRepository.findProjectsWithFilters(
                userId,
                titlePattern,
                customerPattern,
                pageable
        );

        List<ProjectShortResponse> projectShortResponses = projectPage.getContent().stream()
                .map(projectEntity -> {
                    ProjectShortResponse response = projectMapper.toProjectShortResponse(projectEntity);
                    String mainImageUrl = projectImageService.getMainProjectImage(projectEntity.getId());
                    response.setMainImage(mainImageUrl);
                    return response;
                })
                .collect(Collectors.toList());

        ProjectPageResponse projectPageResponse = new ProjectPageResponse();
        projectPageResponse.setPageNumber(projectPage.getNumber());
        projectPageResponse.setTotalPages(projectPage.getTotalPages());
        projectPageResponse.setProjectShortResponses(projectShortResponses);

        return projectPageResponse;
    }

    public ProjectResponse getProjectById(Long id) throws ProjectNotFoundException {
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));

        ProjectImageResponse projectImages = projectImageService.getProjectImage(id);

        ProjectResponse response = projectMapper.toProjectResponse(projectEntity);
        response.setMainImage(projectImages.getImageUrl());
//        response.setUser(userMapperImpl.toUserResponse(userService.getById(projectEntity.getUserId())));

        return response;
    }

    public List<ProjectShortResponse> getAllByUserId(Long userId) {
        return projectRepository.findAllByUserId(userId).stream()
                .map(projectEntity -> {
                    ProjectShortResponse response = projectMapper.toProjectShortResponse(projectEntity);
                    String imageUrl = projectImageService.getMainProjectImage(projectEntity.getId());
                    response.setMainImage(imageUrl);
                    return response;
                })
                .collect(Collectors.toList());
    }
}