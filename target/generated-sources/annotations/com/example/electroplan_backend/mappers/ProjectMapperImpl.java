package com.example.electroplan_backend.mappers;

import com.example.electroplan_backend.dto.entities.ProjectEntity;
import com.example.electroplan_backend.dto.requests.ProjectRequest;
import com.example.electroplan_backend.dto.responses.ProjectResponse;
import com.example.electroplan_backend.dto.responses.ProjectShortResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-17T14:49:51+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public ProjectEntity toProjectEntity(ProjectRequest carRequest) {
        if ( carRequest == null ) {
            return null;
        }

        ProjectEntity projectEntity = new ProjectEntity();

        return projectEntity;
    }

    @Override
    public ProjectResponse toProjectResponse(ProjectEntity carEntity) {
        if ( carEntity == null ) {
            return null;
        }

        ProjectResponse.ProjectResponseBuilder projectResponse = ProjectResponse.builder();

        projectResponse.id( carEntity.getId() );
        projectResponse.title( carEntity.getTitle() );
        projectResponse.customer( carEntity.getCustomer() );
        projectResponse.description( carEntity.getDescription() );
        projectResponse.address( carEntity.getAddress() );
        projectResponse.createdAt( carEntity.getCreatedAt() );
        projectResponse.updatedAt( carEntity.getUpdatedAt() );

        return projectResponse.build();
    }

    @Override
    public ProjectShortResponse toProjectShortResponse(ProjectEntity carEntity) {
        if ( carEntity == null ) {
            return null;
        }

        ProjectShortResponse projectShortResponse = new ProjectShortResponse();

        projectShortResponse.setId( carEntity.getId() );
        projectShortResponse.setTitle( carEntity.getTitle() );
        projectShortResponse.setCustomer( carEntity.getCustomer() );

        return projectShortResponse;
    }
}
