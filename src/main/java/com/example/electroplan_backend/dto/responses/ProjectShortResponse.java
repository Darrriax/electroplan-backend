package com.example.electroplan_backend.dto.responses;

import lombok.Data;

@Data
public class ProjectShortResponse {

    private Long id;

    private String mainImage;

    private String title;

    private String customer;

    private String lastUpdated;

}
