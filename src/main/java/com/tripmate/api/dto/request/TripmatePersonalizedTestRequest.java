package com.tripmate.api.dto.request;

import com.tripmate.api.domain.user.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TripmatePersonalizedTestRequest(

        @Schema(description = "MBTI 검사 결과")
        @Pattern(regexp = "^[EI][NS][FT][JjP]$", message = "MBTI 포맷 오류: E/I + N/S + F/T + J/j/P")
        String mbti,

        @Schema(description = "성별(MALE, FEMALE)")
        @NotNull
        Gender gender,

        @Pattern(regexp = "\\d{6}", message = "생년월일(birthDate) 포맷 오류: YYMMDD")
        @Schema(description = "생년월일 (YYMMDD 형식)")
        String birthDate,

        @Schema(description = "개인화 여행 키워드(최대 3개)")
        @Size(min = 3, max = 3)
        List<String> keywords
) {
}
