package com.example.electroplan_backend.dto.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "project_images")
public class ProjectImage {

    @Id
    private String id;
    private Long projectId;
    private String mainImageUrl;
    private List<String> imagesUrl;

    public void addImageUrl(String imageUrl) {
        imagesUrl.add(imageUrl);
    }

    public void addProjectImage(ProjectImage image) {
        this.mainImageUrl = image.getMainImageUrl();
        imagesUrl.addAll(image.getImagesUrl());
    }

}
