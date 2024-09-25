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
public class MyApplyCompanionListResponse {

    private Long companionId;

    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

    private String openChatLink;

    private boolean reviewYn;

    private String matchingStatus;

    @Schema(description = "호스트 정보")
    private TripHostInfo tripHostInfo;


    @Builder
    @Getter
    public static class TripHostInfo {

        @Schema(description = "동행 모임장의 여행 스타일 키워드 목록")
        private List<String> selectedKeyword;

        @Schema(description = "동행 모임장 여행 스타일")
        private String tripStyle;
        @Schema(description = "동행 모임장 캐릭터 id")
        private String characterId;

    }
}