package com.tripmate.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    private Long kakaoId;

    @NotNull
    private String nickname;
    @NotNull
    private String profileImage;
    @NotNull
    private String thumbnailImage;

}
