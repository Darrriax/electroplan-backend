package com.example.electroplan_backend.services;

import com.example.electroplan_backend.dto.documents.ProjectImage;
import com.example.electroplan_backend.dto.requests.ProjectImageRequest;
import com.example.electroplan_backend.dto.responses.ProjectImageResponse;
import com.example.electroplan_backend.mappers.ProjectImageMapper;
import com.example.electroplan_backend.repositories.ProjectImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectImageService {

    private ProjectImageRepository projectImageRepository;

    private final ProjectImageMapper projectImageMapper;

    //Може вернуть налл
    public String getMainProjectImage(Long projectId) {
        ProjectImage projectImage = projectImageRepository.findByProjectId(projectId);
        return projectImage == null ? null : projectImage.getMainImageUrl();
    }

    public ProjectImageResponse getProjectImage(Long projectId) {
        ProjectImage projectImage = projectImageRepository.findByProjectId(projectId);
        return projectImageMapper.toProjectImageResponse(projectImage);
    }

    public ProjectImageResponse addOrReplaceProjectImage(ProjectImageRequest projectImageRequest) {
        ProjectImage projectImage = projectImageMapper.toProjectImage(projectImageRequest);
        long projectId = projectImage.getProjectId();
        ProjectImage oldProjectImage = projectImageRepository.findByProjectId(projectId);
        if (oldProjectImage != null) {
            oldProjectImage.setMainImageUrl(projectImage.getMainImageUrl());
            oldProjectImage.setImagesUrl(projectImage.getImagesUrl());
            projectImageRepository.save(oldProjectImage);
            return projectImageMapper.toProjectImageResponse(oldProjectImage);
        }
        ProjectImage savedProjectImage = projectImageRepository.save(projectImage);
        return projectImageMapper.toProjectImageResponse(savedProjectImage);
    }


    public ProjectImageResponse addProjectImage(ProjectImageRequest projectImageRequest) {
        ProjectImage projectImage = projectImageMapper.toProjectImage(projectImageRequest);
        long projectId = projectImage.getProjectId();
        ProjectImage oldProjectImage = projectImageRepository.findByProjectId(projectId);
        if (oldProjectImage != null) {
            oldProjectImage.addProjectImage(projectImage);
            return projectImageMapper.toProjectImageResponse(projectImageRepository.save(oldProjectImage));
        }
        return projectImageMapper.toProjectImageResponse(projectImageRepository.save(projectImage));
    }

    public List<ProjectImageResponse> addProjectImages(List<ProjectImageRequest> projectImageRequests) {
        List<ProjectImageResponse> responses = new ArrayList<>(projectImageRequests.size());
        for (ProjectImageRequest projectImageRequest : projectImageRequests) {
            responses.add(addOrReplaceProjectImage(projectImageRequest));
        }

        return responses;
    }


    public List<ProjectImageResponse> getAllProjectImage() {
        List<ProjectImage> projectImages = projectImageRepository.findAll();
        return projectImages.stream().map(projectImageMapper::toProjectImageResponse).toList();
    }


}
