package com.tripmate.api.domain.spot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum SpotTypeGroup {

    ACTIVITY(
            "액티비티",
            Arrays.asList(
                    SpotType.EXPERIENCE,
                    SpotType.CULTURE_AND_ART,
                    SpotType.FESTIVAL_AND_PERFORMANCE,
                    SpotType.TRIP_ITINERARY,
                    SpotType.LEISURE_SPORTS,
                    SpotType.ACCOMMODATION,
                    SpotType.SHOPPING,
                    SpotType.RESTAURANT_AND_CAFE
            )
    ),
    HEALING(
            "힐링",
            Arrays.asList(
                    SpotType.NATURE_AND_REST,
                    SpotType.CULTURE_AND_ART,
                    SpotType.HISTORY,
                    SpotType.ACCOMMODATION,
                    SpotType.SHOPPING,
                    SpotType.RESTAURANT_AND_CAFE
            )
    );

    private final String name;

    private final List<SpotType> categories;
}
