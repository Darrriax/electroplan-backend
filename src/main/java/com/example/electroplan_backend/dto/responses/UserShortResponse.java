package com.example.electroplan_backend.dto.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserShortResponse {
    private Long id;
    private String name;
    private String surname;
    private String fatherName;
    private Integer age;
    private String gender;
    private String additionalInfo;
}
