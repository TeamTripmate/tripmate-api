package com.tripmate.api.dto.companion;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record  HostInfo(
    String profileImage,
    String kakaoNickname,
    String characterName,
    @Schema(description = "유저가 선택했던 키워드")
    List<String> selectedKeyword

) {

}
