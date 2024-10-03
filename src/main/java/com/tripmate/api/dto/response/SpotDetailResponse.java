package com.tripmate.api.dto.response;

import com.tripmate.api.domain.Spot;
import com.tripmate.api.domain.spot.Address;
import com.tripmate.api.domain.spot.Location;
import com.tripmate.api.domain.spot.SpotType;
import com.tripmate.api.domain.user.TripmateCharacter;
import com.tripmate.api.domain.user.TripmateCharacterType;
import com.tripmate.api.dto.companion.CompanionRecruitInfo;
import com.tripmate.integration.tourapi.dto.response.SpotCommonInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SpotDetailResponse(
        @Schema(description = "여행지 ID")
        Long spotId,

        @Schema(description = "여행지 이름")
        String title,

        @Schema(description = "여행지 상세 설명")
        String description,

        @Schema(description = "여행지 타입")
        SpotType spotType,

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
) {
        public static SpotDetailResponse toResponse(
                SpotCommonInfo spotCommonInfo,
                List<TripmateCharacter> recommendedStyles,
                List<CompanionRecruitInfo> companionRecruits
        ) {
                SpotType spotType = SpotType.fromTourApiCategory(spotCommonInfo.cat2());

                return new SpotDetailResponse(
                        spotCommonInfo.contentId(),
                        spotCommonInfo.title(),
                        spotCommonInfo.overview(),
                        spotType,
                        spotCommonInfo.firstImage(),
                        spotCommonInfo.tel(),
                        new Location(
                                spotCommonInfo.mapY(),
                                spotCommonInfo.mapX(),
                                new Address(
                                        spotCommonInfo.addr1(),
                                        spotCommonInfo.addr2()
                                )
                        ),
                        recommendedStyles,
                        companionRecruits
                );
        }
}
