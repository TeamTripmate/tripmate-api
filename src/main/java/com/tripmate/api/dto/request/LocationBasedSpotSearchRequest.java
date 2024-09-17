package com.tripmate.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record LocationBasedSpotSearchRequest(
        @NotNull String latitude,
        @NotNull String longitude,
        @NotNull String range,
        String category
) {
}
