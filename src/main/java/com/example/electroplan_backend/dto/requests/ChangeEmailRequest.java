package com.example.electroplan_backend.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeEmailRequest {
    @Email(message = "Введіть коректний email")
    @NotBlank(message = "Email не може бути порожнім")
    private String newEmail;
}
