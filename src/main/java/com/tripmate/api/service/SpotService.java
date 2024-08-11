package com.tripmate.api.service;

import com.tripmate.api.dto.response.SpotResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpotService {

    public List<SpotResponse> findSpotsByLocation(double latitude, double longitude, double range) {
        return null;
    }
}
