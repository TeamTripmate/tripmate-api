package com.tripmate.api.dto.request;

import com.tripmate.api.domain.SpotCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LocationBasedSpotSearchRequest(
        @Schema(description = "위도", example = "37.7563022")
        String latitude,

        @Schema(description = "경도", example = "128.922632")
        String longitude,

        @Schema(description = "범위(반경)", example = "10000.0")
        String range,

        @Schema(description = "여행지 카테고리(EXPERIENCE, CULTURE_AND_ART, etc.)", example = "EXPERIENCE")
        SpotCategory category
) {
}
