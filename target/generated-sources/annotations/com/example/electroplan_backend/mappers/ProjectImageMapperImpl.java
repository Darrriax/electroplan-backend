package com.example.electroplan_backend.mappers;

import com.example.electroplan_backend.dto.documents.ProjectImage;
import com.example.electroplan_backend.dto.requests.ProjectImageRequest;
import com.example.electroplan_backend.dto.responses.ProjectImageResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-17T14:49:52+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class ProjectImageMapperImpl implements ProjectImageMapper {

    @Override
    public ProjectImage toProjectImage(ProjectImageRequest carImageRequest) {
        if ( carImageRequest == null ) {
            return null;
        }

        ProjectImage projectImage = new ProjectImage();

        projectImage.setProjectId( carImageRequest.getProjectId() );
        projectImage.setMainImageUrl( carImageRequest.getMainImageUrl() );
        projectImage.setImagesUrl( stringArrayToStringList( carImageRequest.getImagesUrl() ) );

        return projectImage;
    }

    @Override
    public ProjectImageResponse toProjectImageResponse(ProjectImage carImage) {
        if ( carImage == null ) {
            return null;
        }

        ProjectImageResponse projectImageResponse = new ProjectImageResponse();

        projectImageResponse.setProjectId( carImage.getProjectId() );

        return projectImageResponse;
    }

    protected List<String> stringArrayToStringList(String[] stringArray) {
        if ( stringArray == null ) {
            return null;
        }

        List<String> list = new ArrayList<String>( stringArray.length );
        for ( String string : stringArray ) {
            list.add( string );
        }

        return list;
    }
}
