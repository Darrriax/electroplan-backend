package com.example.electroplan_backend.dto.requests;


import lombok.Data;

@Data
public class ProjectImageRequest {
    private Long projectId;
    private String mainImageUrl;
    private String[] imagesUrl;

}