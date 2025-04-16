package com.example.electroplan_backend.dto.responses;

import com.example.electroplan_backend.dto.entities.ImageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBannerResponse {
    private String base64Image;
    private ImageType imageType;
    private Integer width;
    private Integer height;
}
