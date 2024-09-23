package com.tripmate.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record CompanionApplyRequest(

    @NotNull
    Long companionId,
    @NotNull
    Long userId
) {}
