package com.tripmate.api.dto.spot;

import com.tripmate.api.domain.spot.Address;
import com.tripmate.api.domain.spot.Location;
import com.tripmate.api.domain.spot.SpotCategory;
import com.tripmate.api.domain.spot.SpotType;
import com.tripmate.integration.tourapi.domain.SpotMediumCategory;
import com.tripmate.integration.tourapi.dto.response.LocationBasedSpotItem;
import com.tripmate.integration.tourapi.dto.response.SpotCommonInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record LocationBasedSpotInfo(
        @Schema(description = "여행지 ID")
        Long spotId,

        @Schema(description = "여행지 이름", example = "설악산 국립공원")
        String title,

        @Schema(description = "여행지 설명", example = "설악산은 사계절 아름다운 경치를 자랑하는 국립공원입니다.")
        String description,

        @Schema(description = "여행지 타입", example = "EXPERIENCE")
        SpotType spotType,

        @Schema(description = "여행지 분류 정보")
        SpotCategory category,

        @Schema(description = "여행지 썸네일 이미지 URL", example = "https://pbs.twimg.com/media/GOSgsYFakAE8UEO.jpg")
        String thumbnailUrl,

        @Schema(description = "여행지 위치 정보")
        Location location,

        @Schema(description = "현재 위치로부터의 거리 (미터 단위)", example="1500")
        String distance,

        @Schema(description = "동행 여부", example = "false")
        boolean companionYn
) {

    public static LocationBasedSpotInfo toLocationBasedSpotInfo(LocationBasedSpotItem from1, SpotCommonInfo from2) {
        SpotMediumCategory mediumCategory = from1.cat2();
        SpotType spotType = SpotType.fromTourApiCategory(mediumCategory);

        return LocationBasedSpotInfo.builder()
                .spotId(from1.contentId())
                .title(from1.title())
                // TODO: 세부 사항 주기
                .description(from2.overview())
                .distance(from1.dist())
                .thumbnailUrl(from1.firstImage())
                .location(new Location(
                        from1.mapY(),
                        from1.mapX(),
                        new Address(from1.addr1(), from1.addr2())
                ))
                .spotType(spotType)
                .category(new SpotCategory(
                        from1.cat1(),
                        mediumCategory.getCategoryName(),
                        from1.cat3().getCategoryName()
                ))
                // TODO: 동행 여부 확인 필요
                .companionYn(false)
                .build();
    }
}
