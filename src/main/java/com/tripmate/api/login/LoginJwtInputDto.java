package com.tripmate.api.login;

import com.tripmate.api.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginJwtInputDto {

    private Long id;
    private String nickname;
    private String thumbnailImageUrl;
    private String profileImageUrl;
    private String accessToken;

    public static LoginJwtInputDto fromUser(UserEntity user) {
        return LoginJwtInputDto.builder()
            .id(user.getKakaoId())
            .profileImageUrl(user.getProfileImage())
            .thumbnailImageUrl(user.getThumbnailImage())
            .nickname(user.getNickname())
            .build();
    }
}
