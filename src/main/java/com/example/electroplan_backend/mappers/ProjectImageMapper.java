package com.example.electroplan_backend.mappers;


import com.example.electroplan_backend.dto.documents.ProjectImage;
import com.example.electroplan_backend.dto.requests.ProjectImageRequest;
import com.example.electroplan_backend.dto.responses.ProjectImageResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectImageMapper {

    ProjectImage toProjectImage(ProjectImageRequest carImageRequest);

    ProjectImageResponse toProjectImageResponse(ProjectImage carImage);

}
