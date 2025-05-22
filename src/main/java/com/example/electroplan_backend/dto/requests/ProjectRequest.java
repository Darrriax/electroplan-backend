package com.example.electroplan_backend.dto.requests;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProjectRequest {
    private Long id;

    @NotBlank(message = "Project name is required")
    private String name;

    @NotBlank(message = "Customer name is required")
    private String customer;

    @NotNull(message = "Project data is required")
    private JsonNode data;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
