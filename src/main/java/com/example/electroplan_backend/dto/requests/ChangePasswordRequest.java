package com.example.electroplan_backend.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "Старий пароль не може бути порожнім")
    private String oldPassword;

    @NotBlank(message = "Пароль не може бути порожнім")
    @Size(min = 6, max = 20, message = "Пароль має бути від 6 до 20 символів")
    private String password;

    @NotBlank(message = "Підтвердження пароля не може бути порожнім")
    @Size(min = 6, max = 20, message = "Пароль має бути від 6 до 20 символів")
    private String passwordConfirmation;
}