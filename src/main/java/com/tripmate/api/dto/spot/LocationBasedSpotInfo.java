package com.tripmate.api.dto.spot;

import com.tripmate.api.domain.spot.Address;
import com.tripmate.api.domain.spot.Location;
import com.tripmate.api.domain.spot.SpotCategory;
import com.tripmate.api.domain.spot.SpotType;
import com.tripmate.integration.tourapi.dto.response.LocationBasedSpotItem;
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

    public static LocationBasedSpotInfo fromLocationBasedSpotItem(LocationBasedSpotItem from) {
        return LocationBasedSpotInfo.builder()
                .spotId(from.contentId())
                .title(from.title())
                .description("Tripmate API에서 지원 예정 내용입니다.")
                .distance(from.dist())
                .thumbnailUrl(from.firstImage2())
                .location(new Location(
                        from.mapX(),
                        from.mapY(),
                        new Address(from.addr1(), from.addr2())
                ))
                .spotType(SpotType.LEISURE_SPORTS)
                .category(new SpotCategory(
                        from.cat1(),
                        from.cat2(),
                        from.cat3()
                ))
                .companionYn(false)
                .build();
    }
}
