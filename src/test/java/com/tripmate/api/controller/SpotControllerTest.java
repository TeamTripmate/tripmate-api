package com.tripmate.api.controller;

import com.tripmate.api.dto.request.LocationBasedSpotSearchRequest;
import com.tripmate.api.dto.spot.LocationBasedSpotInfo;
import com.tripmate.api.service.LocationBasedSpotSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpotController.class)
public class SpotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationBasedSpotSearchService locationBasedSpotSearchService;

    @Test
    @WithMockUser
    public void testGetSpots() throws Exception {
        // given
        LocationBasedSpotSearchRequest request = new LocationBasedSpotSearchRequest(
                "37.1234",
                "122.4321",
                "10000.0",
                null
        );

//        List<LocationBasedSpotRecord> spots = List.of(
//                new LocationBasedSpotRecord(1L, ""),
//                new LocationBasedSpotRecord(2L, "")
//        );

        List<LocationBasedSpotInfo> spots = Collections.emptyList();

        given(locationBasedSpotSearchService.searchLocationBasedSpots(request)).willReturn(spots);

        // when&then
        mockMvc.perform(get("/api/v1/spots")
                        .param("latitude", request.latitude())
                        .param("longitude", request.longitude())
                        .param("range", request.range())
                        .param("category", "EXPERIENCE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$['spots'].length()").value(0));
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[1].id").value(2));
    }
}