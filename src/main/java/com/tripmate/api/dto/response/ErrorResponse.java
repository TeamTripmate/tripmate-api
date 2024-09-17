package com.tripmate.api.dto.response;

public record ErrorResponse(
        String errorCode,
        String errorMessage
) {
}
