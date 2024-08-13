package com.tripmate.api.service;

import com.tripmate.api.dto.response.SpotResponse;
import com.tripmate.api.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository spotRepository;

    public List<SpotResponse> findSpotsByLocation(double latitude, double longitude, double range) {
        return null;
    }
}
