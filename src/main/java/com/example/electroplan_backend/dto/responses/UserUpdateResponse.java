package com.example.electroplan_backend.dto.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateResponse {
    private Long id;
    private String name;
    private String surname;
    private String fatherName;
    private String phoneNumber;
    private String email;
    private Integer age;
    private String gender;
    private String additionalInfo;
}
