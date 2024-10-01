package com.tripmate.integration.tourapi.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tripmate.integration.tourapi.dto.mapper.LocationBasedSpotListDeserializer;
import com.tripmate.integration.tourapi.dto.mapper.SpotDeserializer;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = SpotDeserializer.class)
public record SpotCommonInfoApiResponse(
        @JsonProperty("item")
        List<SpotCommonInfo> spotItems
) {
}
