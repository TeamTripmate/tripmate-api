package com.tripmate.integration.tourapi.dto.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmate.integration.tourapi.dto.response.SpotCommonInfo;
import com.tripmate.integration.tourapi.dto.response.SpotCommonInfoApiResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SpotDeserializer extends JsonDeserializer<SpotCommonInfoApiResponse> {

    private final ObjectMapper mapper;

    public SpotDeserializer() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public SpotCommonInfoApiResponse deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode items = node.findValue("item");

        List<SpotCommonInfo> spotItems = Arrays.stream(mapper.treeToValue(items, SpotCommonInfo[].class)).toList();
        return new SpotCommonInfoApiResponse(spotItems);
    }
}
