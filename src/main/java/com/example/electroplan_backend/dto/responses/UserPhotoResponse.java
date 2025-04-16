package com.example.electroplan_backend.dto.responses;

import com.example.electroplan_backend.dto.entities.ImageType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPhotoResponse {
    private String base64Image;
    private ImageType imageType;
}
