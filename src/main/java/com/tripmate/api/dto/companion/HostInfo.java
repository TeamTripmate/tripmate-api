package com.tripmate.api.dto.companion;

import com.tripmate.api.domain.user.TripmateCharacterType;
import com.tripmate.api.entity.TripStyleEntity;
import com.tripmate.api.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import org.apache.catalina.Host;

@Builder
public record HostInfo(
        @Schema(description = "동행 모임장의 프로필 이미지 URL")
        String profileImage,

        @Schema(description = "동행 모임장의 카카오 닉네임")
        String kakaoNickname,

        @Schema(description = "동행 모임장의 여행 스타일명")
        String characterName,

        @Schema(description = "동행 모임장의 캐릭터 타입")
        TripmateCharacterType characterType,

        @Schema(description = "동행 모임장의 여행 스타일 키워드 목록")
        List<String> selectedKeyword,

        @Schema(description = "동행 모임장과 매칭 비율")
        int matchingRatio
) {

        public static HostInfo fromUser(UserEntity user, TripStyleEntity tripStyle) {
//                TripStyleEntity tripStyle = user.getTripStyle();

                return HostInfo.builder()
                        .profileImage(user.getProfileImage())
                        .kakaoNickname(user.getNickname())
                        .characterName(tripStyle.getStyleName())
                        .characterType(user.getCharacterType())
                        .selectedKeyword(tripStyle.getSelectedKeywords())
                        .matchingRatio(30)
                        .build();
        }
}