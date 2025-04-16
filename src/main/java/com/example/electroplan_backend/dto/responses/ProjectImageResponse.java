package com.example.electroplan_backend.dto.responses;


import lombok.Data;

@Data
public class ProjectImageResponse {
    private Long carId;
    private String mainImageUrl;
    private String[] imagesUrl;
}
