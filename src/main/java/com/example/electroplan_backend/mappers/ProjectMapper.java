package com.example.electroplan_backend.mappers;

import com.example.electroplan_backend.dto.entities.ProjectEntity;
import com.example.electroplan_backend.dto.requests.ProjectRequest;
import com.example.electroplan_backend.dto.responses.ProjectResponse;
import com.example.electroplan_backend.dto.responses.ProjectShortResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectEntity toProjectEntity(ProjectRequest projectRequest);

    ProjectResponse toProjectResponse(ProjectEntity projectEntity);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "updatedAt", target = "updatedAt")
    ProjectShortResponse toProjectShortResponse(ProjectEntity projectEntity);

}
