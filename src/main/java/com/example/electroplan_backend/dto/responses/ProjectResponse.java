package com.example.electroplan_backend.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {

        private Long id;

        private String mainImage;

        private String title;

        private String customer;

        private String description;

        private String address;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        // Тимчасово зберігаємо у вигляді масиву об'єктів або рядків
        private List<Object> projectData;
}