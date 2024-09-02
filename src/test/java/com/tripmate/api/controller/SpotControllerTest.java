package com.tripmate.api.controller;

import com.tripmate.api.dto.response.SpotResponse;
import com.tripmate.api.service.SpotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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
    private SpotService spotService;

    @Test
    @WithMockUser
    public void testGetSpots() throws Exception {
        // given
        String latitude = "37.1234";
        String longitude = "-122.4321";
        String range = "10000.0";

        List<SpotResponse> spots = List.of(
                new SpotResponse(1L, ""),
                new SpotResponse(2L, "")
        );

        given(spotService.findSpotsByLocation(latitude, longitude, range)).willReturn(spots);

        // when&then
        mockMvc.perform(get("/api/v1/spots")
                        .param("latitude", latitude)
                        .param("longitude", longitude)
                        .param("range", range))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }
}