package com.tripmate.api.service;

import com.tripmate.api.repository.SpotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SpotServiceTest {

    @Autowired
    private SpotService spotService;

    @MockBean
    private SpotRepository spotRepository;

    @Test
    public void testFindSpotsByLocation() {
        
    }

}