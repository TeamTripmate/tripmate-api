package com.tripmate.api.service;

import com.tripmate.api.domain.test.TripmatePersonalizedTestResult;
import com.tripmate.api.dto.request.TripmatePersonalizedTestRequest;
import com.tripmate.api.dto.response.TripmatePersonalizedTestResponse;
import com.tripmate.api.entity.TripStyleEntity;
import com.tripmate.api.entity.TripStyleRepository;
import com.tripmate.api.entity.UserEntity;
import com.tripmate.api.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TripmatePersonalizedTestService {

    private final UserRepository userRepository;

    private final TripStyleRepository tripStyleRepository;

    @Transactional
    public TripmatePersonalizedTestResponse submitTripmatePersonalizedTest(
            Long userId,
            TripmatePersonalizedTestRequest request
    ) {
        List<String> keywords = request.keywords().stream().sorted().toList();

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다.", null));

        TripStyleEntity tripStyle = tripStyleRepository.findByKeywords(
                keywords.get(0),
                keywords.get(1),
                keywords.get(2)
        ).orElseThrow(() -> new NoSuchElementException("여행 스타일이 존재하지 않습니다.", null));

        user.applyTripmatePersonalizedTestResult(new TripmatePersonalizedTestResult(
                tripStyle.getId(),
                request.mbti(),
                request.gender(),
                request.birthDate()
        ));

        return new TripmatePersonalizedTestResponse(user.getCharacterType());
    }
}
