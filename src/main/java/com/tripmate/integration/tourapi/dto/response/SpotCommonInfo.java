package com.tripmate.integration.tourapi.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tripmate.integration.tourapi.domain.SpotMediumCategory;
import com.tripmate.integration.tourapi.domain.SpotSmallCategory;
import com.tripmate.integration.tourapi.dto.mapper.SpotDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SpotCommonInfo(

        @JsonProperty("contenttypeid")
        String contentTypeId,

        @JsonProperty("title")
        String title,

        @JsonProperty("tel")
        String tel,

        @JsonProperty("telname")
        String telName,

        @JsonProperty("overview")
        String overview,

        @JsonProperty("cat1")
        String cat1,

        @JsonProperty("cat2")
        SpotMediumCategory cat2,

        @JsonProperty("cat3")
        SpotSmallCategory cat3,

        @JsonProperty("addr1")
        String addr1,

        @JsonProperty("addr2")
        String addr2,

        @JsonProperty("contentid")
        Long contentId,

        @JsonProperty("firstimage")
        String firstImage,

        @JsonProperty("firstimage2")
        String firstImage2,

        @JsonProperty("mapx")
        String mapX,

        @JsonProperty("mapy")
        String mapY,

        @JsonProperty("mlevel")
        int mLevel,

        @JsonProperty("modifiedtime")
        String modifiedTime,

        @JsonProperty("sigungucode")
        String siGunGuCode
) {
}