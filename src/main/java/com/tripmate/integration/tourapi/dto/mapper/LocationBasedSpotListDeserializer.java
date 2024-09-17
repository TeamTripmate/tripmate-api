package com.tripmate.integration.tourapi.dto.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmate.integration.tourapi.dto.response.LocationBasedSpotItem;
import com.tripmate.integration.tourapi.dto.response.LocationBasedSpotApiResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LocationBasedSpotListDeserializer extends JsonDeserializer<LocationBasedSpotApiResponse> {

    private final ObjectMapper mapper;

    public LocationBasedSpotListDeserializer() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public LocationBasedSpotApiResponse deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode items = node.findValue("item");

        List<LocationBasedSpotItem> spotItems = Arrays.stream(mapper.treeToValue(items, LocationBasedSpotItem[].class)).toList();

        return new LocationBasedSpotApiResponse(spotItems);
    }
}
