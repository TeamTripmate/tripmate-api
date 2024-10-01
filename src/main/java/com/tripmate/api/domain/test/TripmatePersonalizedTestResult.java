package com.tripmate.api.domain.test;

import com.tripmate.api.domain.user.Gender;

public record TripmatePersonalizedTestResult(
        Long tripStyleId,
        String mbti,
        Gender gender,
        String birthDate
) {
}
