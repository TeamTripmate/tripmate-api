package com.tripmate.api.dto.companion;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record ReviewInfo(
    @Schema(description = "리뷰 작성자 정보")
    UserInfo userInfo,
    @Schema(description = "리뷰 작성 날짜")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime reviewDate,
    @Schema(description = "좋았어요 리스트")
    List<String> likeList,
    @Schema(description = "아쉬워요 리스트")
    List<String> badList
) {
}
