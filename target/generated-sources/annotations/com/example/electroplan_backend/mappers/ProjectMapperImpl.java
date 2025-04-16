package com.example.electroplan_backend.mappers;

import com.example.electroplan_backend.dto.entities.ProjectEntity;
import com.example.electroplan_backend.dto.requests.ProjectRequest;
import com.example.electroplan_backend.dto.responses.ProjectResponse;
import com.example.electroplan_backend.dto.responses.ProjectShortResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-16T20:25:28+0300",
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

        projectEntity.setDisplayName( carRequest.getDisplayName() );
        projectEntity.setSellerId( carRequest.getSellerId() );
        projectEntity.setBodyType( carRequest.getBodyType() );
        projectEntity.setPrice( carRequest.getPrice() );
        projectEntity.setManufacturer( carRequest.getManufacturer() );
        projectEntity.setVinCode( carRequest.getVinCode() );
        projectEntity.setWasInAccident( carRequest.getWasInAccident() );
        projectEntity.setIsTrade( carRequest.getIsTrade() );
        projectEntity.setIsAvailable( carRequest.getIsAvailable() );
        projectEntity.setMileage( carRequest.getMileage() );
        projectEntity.setTechnicalCondition( carRequest.getTechnicalCondition() );

        return projectEntity;
    }

    @Override
    public ProjectResponse toProjectResponse(ProjectEntity carEntity) {
        if ( carEntity == null ) {
            return null;
        }

        ProjectResponse projectResponse = new ProjectResponse();

        projectResponse.setId( carEntity.getId() );
        projectResponse.setDisplayName( carEntity.getDisplayName() );
        projectResponse.setBodyType( carEntity.getBodyType() );
        projectResponse.setPrice( carEntity.getPrice() );
        projectResponse.setManufacturer( carEntity.getManufacturer() );
        projectResponse.setVinCode( carEntity.getVinCode() );
        projectResponse.setWasInAccident( carEntity.getWasInAccident() );
        projectResponse.setIsTrade( carEntity.getIsTrade() );
        projectResponse.setIsAvailable( carEntity.getIsAvailable() );
        projectResponse.setMileage( carEntity.getMileage() );
        projectResponse.setTechnicalCondition( carEntity.getTechnicalCondition() );

        return projectResponse;
    }

    @Override
    public ProjectShortResponse toProjectShortResponse(ProjectEntity carEntity) {
        if ( carEntity == null ) {
            return null;
        }

        ProjectShortResponse projectShortResponse = new ProjectShortResponse();

        projectShortResponse.setId( carEntity.getId() );
        projectShortResponse.setDisplayName( carEntity.getDisplayName() );
        projectShortResponse.setPrice( carEntity.getPrice() );
        projectShortResponse.setVinCode( carEntity.getVinCode() );
        projectShortResponse.setWasInAccident( carEntity.getWasInAccident() );
        projectShortResponse.setMileage( carEntity.getMileage() );

        return projectShortResponse;
    }
}
