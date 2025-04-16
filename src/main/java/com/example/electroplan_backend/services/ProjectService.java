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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public ProjectPageResponse searchProjects(String manufacturer, String bodyType, Integer priceFrom, Integer priceTo, Integer mileageFrom, Integer mileageTo, String displayName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        String displayNamePattern = null;
        if (displayName != null && !displayName.isEmpty()) {
            displayNamePattern = "%" + displayName + "%";
        }

        String manufacturerPattern = null;
        if (manufacturer != null && !manufacturer.isEmpty()) {
            manufacturerPattern = "%" + manufacturer + "%";
        }


        Page<ProjectEntity> projectPage = projectRepository.findProjectsWithFilters(manufacturerPattern, bodyType, priceFrom, priceTo, mileageFrom, mileageTo, displayNamePattern, pageable);




        List<ProjectShortResponse> projectShortResponses = projectPage.getContent().stream()
                .map(projectEntity -> {
                    ProjectShortResponse projectShortResponse = projectMapper.toProjectShortResponse(projectEntity);
                    String ImageUrl = projectImageService.getMainProjectImage(projectEntity.getId());
                    projectShortResponse.setMainImageURL(ImageUrl);
                    return projectShortResponse;
                }).collect(Collectors.toList());

        ProjectPageResponse projectPageResponse = new ProjectPageResponse();
        projectPageResponse.setPageNumber(projectPage.getNumber());
        projectPageResponse.setTotalPages(projectPage.getTotalPages());
        projectPageResponse.setProjectShortResponses(projectShortResponses);

        return projectPageResponse;
    }



    public ProjectResponse getProjectById(Long id) throws ProjectNotFoundException, UsernameNotFoundException {
        ProjectEntity projectEntity = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
        ProjectImageResponse projectImages = projectImageService.getProjectImage(id);

        ProjectResponse projectResponse = projectMapper.toProjectResponse(projectEntity);

        projectResponse.setOwner(userMapperImpl.toUserResponse(userService.getById(projectEntity.getSellerId())));

        projectResponse.setImages(projectImages);
        return projectResponse;
    }


public List<ProjectShortResponse> getAllByUserId(Long id) {
    return projectRepository.findAllBySellerId(id).stream()
            .map(projectEntity -> {
                ProjectShortResponse projectShortResponse = projectMapper.toProjectShortResponse(projectEntity);
                ProjectImageResponse projectImages = projectImageService.getProjectImage(projectEntity.getId());
                projectShortResponse.setMainImageURL(projectImages.getMainImageUrl());
                return projectShortResponse;
            })
            .collect(Collectors.toList());
}
}
