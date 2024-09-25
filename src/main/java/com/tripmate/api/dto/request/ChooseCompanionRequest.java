package com.tripmate.api.dto.request;

import lombok.Builder;

@Builder
public record ChooseCompanionRequest(
    Long companionId,
    Long userId
) {

}
