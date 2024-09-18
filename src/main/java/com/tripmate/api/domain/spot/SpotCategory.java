package com.tripmate.api.domain.spot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpotCategory {

    EXPERIENCE("체험"),
    CULTURE_AND_ART("문화예술"),
    FESTIVAL_AND_PERFORMANCE("축제∙공연"),
    NATURE_AND_REST("자연∙휴양"),
    TRIP_ITINERARY("여행코스"),
    HISTORY("역사"),
    LEISURE_SPORTS("레포츠"),
    ACCOMMODATION("숙박"),
    SHOPPING("쇼핑"),
    RESTAURANT_AND_CAFE("맛집∙카페");

    private final String name;
}
