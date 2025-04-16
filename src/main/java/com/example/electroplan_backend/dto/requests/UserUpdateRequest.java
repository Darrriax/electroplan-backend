package com.example.electroplan_backend.dto.requests;


import com.example.electroplan_backend.validations.AtLeastOneFieldNotEmpty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AtLeastOneFieldNotEmpty(message = "At least one field must be filled")
public class UserUpdateRequest {

    @Size(max = 50, message = "Ім'я має містити не більше 50 символів")
    private String name;

    @Size(max = 50, message = "Прізвище має містити не більше 50 символів")
    private String surname;

    @Size(max = 50, message = "По-батькові має містити не більше 50 символів")
    private String fatherName;

    @Size(min = 6, max = 20, message = "Пароль має бути від 6 до 20 символів")
    private String password;

    @Pattern(
        regexp = "^38\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}$",
        message = "Введіть коректний номер телефону у форматі +1234567890"
    )
    private String phoneNumber;

    @Min(value = 18, message = "Вік має бути не менше 18 років")
    @Max(value = 100, message = "Вік має бути не більше 100 років")
    private Integer age;

    @Pattern(
        regexp = "^(Male|Female)$",
        message = "Стать має бути або 'Male', або 'Female'"
    )
    private String gender;

    @Size(max = 255, message = "Додаткова інформація має містити не більше 255 символів")
    private String additionalInfo;
}
