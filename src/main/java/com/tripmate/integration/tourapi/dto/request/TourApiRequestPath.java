package com.tripmate.integration.tourapi.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TourApiRequestPath {
    LOCATION_BASED_SPOT("B551011/KorService1/locationBasedList1");

    private final String path;
}
