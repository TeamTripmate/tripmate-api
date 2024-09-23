package com.tripmate.api.dto.companion;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ReviewInfo(
    @Schema(description = "리뷰 작성자 정보")
    UserInfo userInfo,
    @Schema(description = "리뷰 작성 날짜")
    String reviewDate,
    @Schema(description = "좋았어요 리스트")
    List<String> likeList,
    @Schema(description = "아쉬워요 리스트")
    List<String> badList
) {

}
