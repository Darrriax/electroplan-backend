package com.example.electroplan_backend.dto.requests;

import com.example.electroplan_backend.validations.PasswordsMatch;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@PasswordsMatch
public class UserRegistrationRequest {

    @NotBlank(message = "Ім'я не може бути порожнім")
    @Size(max = 50, message = "Ім'я має містити не більше 50 символів")
    private String name;

    @NotBlank(message = "Прізвище не може бути порожнім")
    @Size(max = 50, message = "Прізвище має містити не більше 50 символів")
    private String surname;

    @NotBlank(message = "Номер телефону не може бути порожнім")
    @Pattern(regexp = "^38\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}$", message = "Введіть коректний номер телефону")
    private String phoneNumber;

    @NotBlank(message = "Email не може бути порожнім")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Введіть коректний email"
    )
    private String email;

    @NotBlank(message = "Пароль не може бути порожнім")
    @Size(min = 6, max = 20, message = "Пароль має бути від 6 до 20 символів")
    private String password;

    @NotBlank(message = "Підтвердження пароля не може бути порожнім")
    @Size(min = 6, max = 20, message = "Пароль має бути від 6 до 20 символів")
    private String passwordConfirmation;
}