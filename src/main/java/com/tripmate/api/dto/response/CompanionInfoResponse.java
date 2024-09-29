package com.tripmate.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tripmate.api.dto.companion.HostInfo;
import com.tripmate.api.dto.companion.ReviewInfo;
import com.tripmate.api.entity.CompanionEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record CompanionInfoResponse(
    String title,

    String spotId,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime date,

    boolean accompanyYn,

    String chatLink,

    String description,

    @Schema(description = "성별", example = "남자")
    String gender,

    @Schema(description = "나이대", example = "20대")
    String ageRange,

    @Schema(description = "호스트 정보")
    HostInfo hostInfo,

    @Schema(description = "이전 동행자 리뷰 정보 리스트")
    List<ReviewInfo> reviewInfos,

    @Schema(description = "이전 동행자 리뷰 태그 순위")
    List<String> reviewRanks
) {

    public static CompanionInfoResponse toResponse(CompanionEntity entity, HostInfo hostInfo,
        List<ReviewInfo> reviewInfos, List<String> reviewRanks, String gender, String ageRange) {

        return CompanionInfoResponse.builder()
            .title(entity.getTitle())
            .spotId(entity.getSpotId().toString())  // Long을 String으로 변환
            .gender(gender)
            .ageRange(ageRange)
            .date(entity.getStartDate())  // LocalDateTime 사용
            // TODO : 유형 정보 enum 필요.. companionType 이거 유형 왜 있냐...
            .accompanyYn("ACCOMPANY".equals(entity.getCompanionType()))  // 동행 여부를 boolean으로 변환
            .chatLink(entity.getOpenChatLink())
            .description(entity.getDescription())
            .hostInfo(hostInfo)  // 호스트 정보
            .reviewInfos(reviewInfos)  // 리뷰 정보 리스트
            .reviewRanks(reviewRanks)  // 리뷰 태그 순위
            .build();
    }
}
