package com.example.electroplan_backend.dto.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProjectPageResponse {

        private Integer pageNumber;

        private Integer totalPages;

        private List<ProjectShortResponse> projectShortResponses;

}
