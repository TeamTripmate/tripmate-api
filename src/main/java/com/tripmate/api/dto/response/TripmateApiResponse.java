package com.tripmate.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TripmateApiResponse", description = "공통 API 응답 객체")
public record TripmateApiResponse<T>(
        @Schema(description = "API 요청 성공 여부", example = "true") boolean success,
        @Schema(description = "API 응답 데이터", nullable = true) T data,
        @Schema(description = "API 요청 에러", nullable = true) ErrorResponse error
) {

    public static <T> TripmateApiResponse<T> success(T data) {
        return new TripmateApiResponse<>(true, data, null);
    }

    public static <T> TripmateApiResponse<T> fail(String errorCode, String errorMessage) {
        return new TripmateApiResponse<>(false, null, new ErrorResponse(errorCode, errorMessage));
    }
}
