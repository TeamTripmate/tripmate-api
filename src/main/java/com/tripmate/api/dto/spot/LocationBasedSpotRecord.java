package com.tripmate.api.dto.spot;

import lombok.Builder;

@Builder
public record LocationBasedSpotRecord(
        Long id,
        String title,
        String description,
        String thumbnailUrl,
        String latitude,
        String longitude,
        String distance
) {
}
