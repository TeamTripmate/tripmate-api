package com.tripmate.api.dto.response;

import com.tripmate.api.dto.spot.LocationBasedSpotRecord;
import lombok.Builder;

import java.util.List;

@Builder
public record LocationBasedSpotListResponse(
        List<LocationBasedSpotRecord> spots
) {
}
