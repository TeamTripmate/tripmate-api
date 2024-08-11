package com.tripmate.api.service;

import com.tripmate.api.repository.SpotRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SpotServiceTest {

    private SpotService spotService;

    private SpotRepository spotRepository;

}