package com.example.electroplan_backend.mappers;

import com.example.electroplan_backend.dto.entities.ProjectEntity;
import com.example.electroplan_backend.dto.requests.ProjectRequest;
import com.example.electroplan_backend.dto.responses.ProjectResponse;
import com.example.electroplan_backend.dto.responses.ProjectShortResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectEntity toProjectEntity(ProjectRequest carRequest);

    ProjectResponse toProjectResponse(ProjectEntity carEntity);

    ProjectShortResponse toProjectShortResponse(ProjectEntity carEntity);

}
