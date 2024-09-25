package com.tripmate.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyCollectCompanionListResponse {

    private Long companionId;

    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

    @Schema(description = "동행 모집 상태")
    private String companionStatus;

    @Schema(description = "신청자 정보")
    private List<ApplicantInfo> applicantInfo;


    @Builder
    @Getter
    public static class ApplicantInfo {

        @Schema(description = "신청자 유저 id")
        private Long userId;

        @Schema(description = "신청자의 여행 스타일 키워드 목록")
        private List<String> selectedKeyword;

        @Schema(description = "신청자 여행 스타일")
        private String tripStyle;
        @Schema(description = "신청자 캐릭터 id")
        private String characterId;

    }

}