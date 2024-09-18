package com.tripmate.api.dto.response;

import com.tripmate.api.dto.spot.LocationBasedSpotInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "위치 기반 여행지 목록 응답 객체")
@Builder
public record LocationBasedSpotListResponse(
        @Schema(description = "위치 기반 여행지 목록")
        List<LocationBasedSpotInfo> spots
) {
}
