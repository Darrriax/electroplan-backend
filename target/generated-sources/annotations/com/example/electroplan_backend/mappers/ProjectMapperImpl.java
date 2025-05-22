package com.example.electroplan_backend.mappers;

import com.example.electroplan_backend.dto.entities.ProjectEntity;
import com.example.electroplan_backend.dto.requests.ProjectRequest;
import com.example.electroplan_backend.dto.responses.ProjectResponse;
import com.example.electroplan_backend.dto.responses.ProjectShortResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-22T15:24:59+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public ProjectEntity toProjectEntity(ProjectRequest projectRequest) {
        if ( projectRequest == null ) {
            return null;
        }

        ProjectEntity projectEntity = new ProjectEntity();

        projectEntity.setId( projectRequest.getId() );
        projectEntity.setName( projectRequest.getName() );
        projectEntity.setCustomer( projectRequest.getCustomer() );
        projectEntity.setData( projectRequest.getData() );
        projectEntity.setCreatedAt( projectRequest.getCreatedAt() );
        projectEntity.setUpdatedAt( projectRequest.getUpdatedAt() );

        return projectEntity;
    }

    @Override
    public ProjectResponse toProjectResponse(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }

        ProjectResponse.ProjectResponseBuilder projectResponse = ProjectResponse.builder();

        projectResponse.id( projectEntity.getId() );
        projectResponse.name( projectEntity.getName() );
        projectResponse.customer( projectEntity.getCustomer() );
        projectResponse.data( projectEntity.getData() );
        projectResponse.createdAt( projectEntity.getCreatedAt() );
        projectResponse.updatedAt( projectEntity.getUpdatedAt() );

        return projectResponse.build();
    }

    @Override
    public ProjectShortResponse toProjectShortResponse(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }

        ProjectShortResponse.ProjectShortResponseBuilder projectShortResponse = ProjectShortResponse.builder();

        projectShortResponse.name( projectEntity.getName() );
        projectShortResponse.customer( projectEntity.getCustomer() );
        projectShortResponse.updatedAt( projectEntity.getUpdatedAt() );
        projectShortResponse.id( projectEntity.getId() );

        return projectShortResponse.build();
    }
}
