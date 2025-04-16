package com.example.electroplan_backend.dto.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectRequest {

    private String displayName;

    private Long sellerId;

    private String bodyType;

    private Integer price;

    private String manufacturer;

    private String vinCode;

    private Boolean wasInAccident;

    private Boolean isTrade;

    private Boolean isAvailable;

    private Integer mileage;

    private String technicalCondition;
}
