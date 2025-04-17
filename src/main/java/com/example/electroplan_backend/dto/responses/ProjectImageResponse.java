package com.example.electroplan_backend.dto.responses;


import lombok.Data;

@Data
public class ProjectImageResponse {
    private Long projectId;
    private String imageUrl;
}
