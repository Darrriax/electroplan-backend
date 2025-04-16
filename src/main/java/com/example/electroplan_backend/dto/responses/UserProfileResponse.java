package com.example.electroplan_backend.dto.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserProfileResponse {
    private Long id;
    private String name;
    private String surname;
    private String fatherName;
    private String email;
    private String phoneNumber;
    private Integer age;
    private String gender;
    private String additionalInfo;

    private List<ProjectShortResponse> usersProjects;

}
