package com.tripmate.api.dto.companion;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserInfo(
    @Schema(description = "작성자 프로필 이미지")
    String profileImage,
    @Schema(description = "작성자 카카오 닉네임")
    String kakaoNickname,
    @Schema(description = "동행 모임장의 여행 스타일명")
    String characterName,
    @Schema(description = "동행 모임장의 캐릭터 타입")
    String characterType
) {

}
