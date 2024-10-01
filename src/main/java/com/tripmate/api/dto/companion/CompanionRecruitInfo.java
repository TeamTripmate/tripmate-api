package com.tripmate.api.dto.companion;

import com.tripmate.api.entity.CompanionEntity;
import com.tripmate.api.entity.TripStyleEntity;
import com.tripmate.api.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CompanionRecruitInfo(

        @Schema(description = "동행 ID")
        Long companionId,

        @Schema(description = "동행 모집장 정보")
        HostInfo hostInfo,

        @Schema(description = "동행모집 제목", example = "강릉 인근에서 같이 식사할 사람 구해요! (선착 1명)")
        String title,

        @Schema(description = "선호 성별", example = "남자만")
        String gender,

        @Schema(description = "나이대", example = "20대")
        String ageRange
) {

        public static CompanionRecruitInfo fromCompanion(CompanionEntity companion, UserEntity host, TripStyleEntity tripStyle) {
                return CompanionRecruitInfo.builder()
                        .companionId(companion.getId())
                        .hostInfo(HostInfo.fromUser(host, tripStyle))
                        .title(companion.getTitle())
                        .gender(companion.isSameAgeYn() ? host.getGender().getGenderName() + "만" : "성별무관")
                        .ageRange(host.getAgeRange())
                        .build();
        }
}
