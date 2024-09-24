package com.tripmate.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
public record MypageUserInfoResponse(

    @Schema(description = "유저가 선택했던 키워드")
    List<String> selectedKeyword,

    @Schema(description = "캐릭터 id")
    String characterId,

    @Schema(description = "여행 스타일")
    String tripStyle,

    String nickname,
    String thumbnailImageUrl,
    String profileImageUrl

) {

}
