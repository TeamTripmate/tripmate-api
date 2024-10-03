package com.tripmate.integration.tourapi.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpotContentType {

    TOURIST_ATTRACTIVENESS("12", "관광지"),
    CULTURAL_FACILITY("14", "문화시설"),
    FESTIVAL_AND_PERFORMANCE("15", "축제/공연/행사"),
    TRIP_ITINERARY("25", "여행코스"),
    LEISURE_SPORTS("28", "레포츠"),
    ACCOMMODATION("32", "숙박"),
    SHOPPING("38", "쇼핑"),
    FOOD("39", "음식");

    private final String contentId;

    private final String name;
}
