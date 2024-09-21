package com.tripmate.api.dto.response;

import com.tripmate.api.dto.companion.HostInfo;
import com.tripmate.api.dto.companion.ReviewInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record CompanionInfoResponse(
    String title,
    String spotId,
    String date,
    boolean accompanyYn,
    String chatLink,
    String description,
    @Schema(description = "호스트 정보")
    HostInfo hostInfo,

    @Schema(description = "이전 동행자 리뷰 정보 리스트")
    List<ReviewInfo> reviewInfos,

    @Schema(description = "이전 동행자 리뷰 태그 순위")
    List<String> reviewRanks
) {

}
