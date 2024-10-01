package com.tripmate.api.dto.response;

import com.tripmate.api.domain.user.TripmateCharacterType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;

@Builder
public record MypageUserInfoResponse(

    @Schema(description = "유저가 선택했던 키워드")
    List<String> selectedKeyword,

    @Schema(description = "캐릭터 id")
    TripmateCharacterType characterId,

    @Schema(description = "여행 스타일")
    String tripStyle,

    String nickname,

    String thumbnailImageUrl,

    String profileImageUrl
) {

    public MypageUserInfoResponse(
            String keyword1, String keyword2, String keyword3,
            TripmateCharacterType characterId, String tripStyle, String nickname,
            String thumbnailImageUrl, String profileImageUrl
    ) {
        this(
            Arrays.asList(keyword1, keyword2, keyword3),
            characterId,
            tripStyle,
            nickname,
            thumbnailImageUrl,
            profileImageUrl
        );
    }
}
