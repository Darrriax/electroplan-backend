package com.example.electroplan_backend.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReloginResponse {
    private String token;
    private UserProfileResponse user;
    private String message;
}
