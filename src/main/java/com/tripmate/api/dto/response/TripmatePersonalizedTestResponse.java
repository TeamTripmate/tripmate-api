package com.tripmate.api.dto.response;

import com.tripmate.api.domain.user.TripmateCharacterType;
import io.swagger.v3.oas.annotations.media.Schema;

public record TripmatePersonalizedTestResponse(

        @Schema(description = "Tripmate 캐릭터 타입")
        TripmateCharacterType characterType
) {
}
