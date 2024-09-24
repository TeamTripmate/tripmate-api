package com.tripmate.api.dto.response;

import com.tripmate.api.domain.spot.Location;
import com.tripmate.api.domain.user.TripmateCharacter;
import com.tripmate.api.dto.companion.CompanionRecruitInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SpotDetailResponse(
        @Schema(description = "여행지 ID")
        Long spotId,

        @Schema(description = "여행지 이름")
        String title,

        @Schema(description = "여행지 상세 설명")
        String description,

        @Schema(description = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5rt0xywjqOvvUPnKPIfSOkd5_RnWQ0vLhjQ&s")
        String imageUrl,

        @Schema(description = "010-0000-0000")
        String phoneNumber,

        @Schema(description = "여행지 위치")
        Location location,

        @Schema(description = "여행지 추천 스타일")
        List<TripmateCharacter> recommendedStyles,

        @Schema(description = "동행 모집 목록")
        List<CompanionRecruitInfo> companionRecruits

        // TODO: 여행지 카테고리는 당장 필요없어 보임
) {
}
