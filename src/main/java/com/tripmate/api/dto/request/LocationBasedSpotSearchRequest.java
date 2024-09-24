package com.tripmate.api.dto.request;

import com.tripmate.api.domain.spot.SpotType;
import com.tripmate.api.domain.spot.SpotTypeGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LocationBasedSpotSearchRequest(

        @Schema(description = "여행지 검색 타입(RANDOM, AROUND_ME)", example = "AROUND_ME")
        @NotNull(message = "검색 타입을 반드시 입력해야 합니다.")
        SpotSearchType searchType,

        @Schema(description = "여행지 타입 그룹(ACTIVITY, HEALING)", example = "ACTIVITY")
        SpotTypeGroup spotTypeGroup,

        @Schema(description = "여행지 타입(EXPERIENCE, CULTURE_AND_ART, etc.)", example = "EXPERIENCE")
        SpotType spotType,

        @Schema(description = "위도", example = "37.7563022")
        @NotBlank(message = "위치(위도)를 반드시 입력해야 합니다.")
        String latitude,

        @Schema(description = "경도", example = "128.922632")
        @NotBlank(message = "위치(경도)를 반드시 입력해야 합니다.")
        String longitude,

        @Schema(description = "범위(반경)", example = "10000.0")
        String range
) {
}
