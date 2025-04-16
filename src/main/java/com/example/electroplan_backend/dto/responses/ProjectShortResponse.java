package com.example.electroplan_backend.dto.responses;

import lombok.Data;

@Data
public class ProjectShortResponse {

        private String mainImageURL;

        private Long id;

        private String displayName;

        private Integer price;

        private String vinCode;

        private Boolean wasInAccident;

        private Integer mileage;

}
