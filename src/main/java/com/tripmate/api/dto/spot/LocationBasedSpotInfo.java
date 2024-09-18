package com.tripmate.api.dto.spot;

import com.tripmate.api.domain.spot.Address;
import com.tripmate.api.domain.spot.Location;
import com.tripmate.integration.tourapi.dto.response.LocationBasedSpotItem;
import lombok.Builder;

@Builder
public record LocationBasedSpotInfo(
        Long spotId,
        String title,
        String description,
        String thumbnailUrl,
        String distance,
        Location location,
        boolean companionYn
) {

    public static LocationBasedSpotInfo fromLocationBasedSpotItem(LocationBasedSpotItem from) {
        return LocationBasedSpotInfo.builder()
                .spotId(from.contentId())
                .title(from.title())
                .distance(from.dist())
                .thumbnailUrl(from.firstImage2())
                .location(new Location(
                        from.mapX(),
                        from.mapY(),
                        new Address(from.addr1(), from.addr2())
                ))
                .build();
    }
}
