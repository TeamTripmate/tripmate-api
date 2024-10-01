package com.tripmate.api.domain.spot;

import com.tripmate.integration.tourapi.domain.SpotMediumCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;

@Getter
@RequiredArgsConstructor
public enum SpotType {

    EXPERIENCE("체험", "12", EnumSet.of(SpotMediumCategory.A0203, SpotMediumCategory.A0102)),
    CULTURE_AND_ART("문화예술", "14", EnumSet.of(SpotMediumCategory.A0206)),
    FESTIVAL_AND_PERFORMANCE("축제∙공연", "15", EnumSet.of(SpotMediumCategory.A0207, SpotMediumCategory.A0208)),
    NATURE_AND_REST("자연∙휴양", "12", EnumSet.of(
            SpotMediumCategory.A0101, SpotMediumCategory.A0102, SpotMediumCategory.A0202
    )),
    TRIP_ITINERARY("여행코스", "25", EnumSet.of(
            SpotMediumCategory.C0112, SpotMediumCategory.C0113, SpotMediumCategory.C0114,
            SpotMediumCategory.C0115, SpotMediumCategory.C0116, SpotMediumCategory.C0117
    )),
    HISTORY("역사", "12", EnumSet.of(SpotMediumCategory.A0201, SpotMediumCategory.A0205)),
    LEISURE_SPORTS("레포츠", "28", EnumSet.of(
            SpotMediumCategory.A0301, SpotMediumCategory.A0302, SpotMediumCategory.A0303,
            SpotMediumCategory.A0304, SpotMediumCategory.A0305
    )),
    ACCOMMODATION("숙박", "32", EnumSet.of(SpotMediumCategory.B0201)),
    SHOPPING("쇼핑", "38", EnumSet.of(SpotMediumCategory.A0401)),
    RESTAURANT_AND_CAFE("맛집∙카페", "39", EnumSet.of(SpotMediumCategory.A0502)),
    UNKNOWN("알수없음", "00", EnumSet.noneOf(SpotMediumCategory.class));

    private final String typeName;

    private final String tourApiContentTypeId;

    private final EnumSet<SpotMediumCategory> tourApiCategorySet;

    public static SpotType fromTourApiCategory(final SpotMediumCategory category) {
        for (final SpotType type : SpotType.values()) {
            if (type.tourApiCategorySet.contains(category)) {
                return type;
            }
        }

        return UNKNOWN;
    }
}
