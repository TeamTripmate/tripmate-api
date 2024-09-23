package com.tripmate.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public record CollectCompanionRequest(

    @Schema(description = "여행지 Id", example = "1L")
    @NotNull(message = "spotId is required")
    Long spotId,

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime date,

    String title,

    String description,

    String type,

    boolean sameGenderYn,
    boolean sameAgeYn,

    String openChatLink,

    Long creatorId

) {

}
