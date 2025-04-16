package com.example.electroplan_backend.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPhoneRequest {

    @NotNull(message = "Id не може бути порожнім")
    private Long userId;

}
