package com.tripmate.integration.tourapi.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tripmate.integration.tourapi.domain.SpotMediumCategory;
import com.tripmate.integration.tourapi.domain.SpotSmallCategory;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LocationBasedSpotItem(
        @JsonProperty("addr1") String addr1,

        @JsonProperty("addr2") String addr2,

        @JsonProperty("areacode") String areaCode,

        @JsonProperty("booktour") String bookTour,

        @JsonProperty("cat1") String cat1,

        @JsonProperty("cat2") SpotMediumCategory cat2,

        @JsonProperty("cat3") SpotSmallCategory cat3,

        @JsonProperty("contentid") Long contentId,

        @JsonProperty("contenttypeid") String contentTypeId,

        @JsonProperty("createdtime") String createdTime,

        @JsonProperty("dist") String dist,

        @JsonProperty("firstimage") String firstImage,

        @JsonProperty("firstimage2") String firstImage2,

        @JsonProperty("cpyrhtDivCd") String cpyrhtDivCd,

        @JsonProperty("mapx") String mapX,

        @JsonProperty("mapy") String mapY,

        @JsonProperty("mlevel") int mLevel,

        @JsonProperty("modifiedtime") String modifiedTime,

        @JsonProperty("sigungucode") String siGunGuCode,

        @JsonProperty("tel") String tel,

        @JsonProperty("title") String title
) {
}
