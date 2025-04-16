package com.example.electroplan_backend.dto.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectResponse {

        private Long id;

        private String displayName;

        private UserShortResponse owner;

        private String bodyType;

        private Integer price;

        private String manufacturer;

        private String vinCode;

        private Boolean wasInAccident;

        private Boolean isTrade;

        private Boolean isAvailable;

        private Integer mileage;

        private String technicalCondition;

        private ProjectImageResponse images;
}
