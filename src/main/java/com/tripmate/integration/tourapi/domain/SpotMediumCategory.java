package com.tripmate.integration.tourapi.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpotMediumCategory {
    A0101("자연관광지"),
    A0102("관광자원"),
    A0201("역사관광지"),
    A0202("휴양관광지"),
    A0203("체험관광지"),
    A0204("산업관광지"),
    A0205("건축/조형물"),
    A0206("문화시설"),
    A0207("축제"),
    A0208("공연/행사"),
    C0112("가족코스"),
    C0113("나홀로코스"),
    C0114("힐링코스"),
    C0115("도보코스"),
    C0116("캠핑코스"),
    C0117("맛코스"),
    A0301("레포츠소개"),
    A0302("육상 레포츠"),
    A0303("수상 레포츠"),
    A0304("항공 레포츠"),
    A0305("복합 레포츠"),
    B0201("숙박시설"),
    A0401("쇼핑"),
    A0502("음식"),
    UNKNOWN("기타");

    private final String categoryName;

    public static SpotMediumCategory fromCode(final String code) {
        for (final SpotMediumCategory category : SpotMediumCategory.values()) {
            if (category.getCategoryName().equals(code)) {
                return category;
            }
        }

        return UNKNOWN;
    }
}
