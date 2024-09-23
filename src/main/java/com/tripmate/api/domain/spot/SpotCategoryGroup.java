package com.tripmate.api.domain.spot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum SpotCategoryGroup {

    ACTIVITY(
            "액티비티",
            Arrays.asList(
                    SpotCategory.EXPERIENCE,
                    SpotCategory.CULTURE_AND_ART,
                    SpotCategory.FESTIVAL_AND_PERFORMANCE,
                    SpotCategory.TRIP_ITINERARY,
                    SpotCategory.LEISURE_SPORTS,
                    SpotCategory.ACCOMMODATION,
                    SpotCategory.SHOPPING,
                    SpotCategory.RESTAURANT_AND_CAFE
            )
    ),
    HEALING(
            "힐링",
            Arrays.asList(
                    SpotCategory.NATURE_AND_REST,
                    SpotCategory.CULTURE_AND_ART,
                    SpotCategory.HISTORY,
                    SpotCategory.ACCOMMODATION,
                    SpotCategory.SHOPPING,
                    SpotCategory.RESTAURANT_AND_CAFE
            )
    );

    private final String name;

    private final List<SpotCategory> categories;
}
