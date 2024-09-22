package com.tripmate.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

public record CollectCompanionRequest(

    @Schema(description = "여행지 Id", example = "1L")
    @NotNull(message = "spotId is required")
    Long spotId,

    String date,

    String title,

    String description,

    String type,

    boolean sameGenderYn,
    boolean sameAgeYn,

    String openChatLink,

    Long creatorId

) {

}
