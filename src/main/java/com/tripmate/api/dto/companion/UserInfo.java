package com.tripmate.api.dto.companion;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserInfo(
    @Schema(description = "작성자 프로필 이미지")
    String profileImage,
    @Schema(description = "작성자 카카오 닉네임")
    String kakaoNickname,
    @Schema(description = "작성자 캐릭터 이름")
    String characterName
) {

}
