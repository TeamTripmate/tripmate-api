package com.tripmate.api.dto.request;

import com.tripmate.api.domain.SpotCategory;
import jakarta.validation.constraints.NotNull;

public record LocationBasedSpotSearchRequest(
        @NotNull String latitude,
        @NotNull String longitude,
        @NotNull String range,
        SpotCategory category
) {
}
