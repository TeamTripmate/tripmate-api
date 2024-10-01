package com.tripmate.api.entity;

import com.tripmate.api.domain.test.TripmatePersonalizedTestResult;
import com.tripmate.api.domain.user.Gender;
import com.tripmate.api.domain.user.TripmateCharacterType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends AbstractEntity {

    @Id
    private Long kakaoId;

    @NotNull
    private String nickname;

    @NotNull
    private String profileImage;

    @NotNull
    private String thumbnailImage;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String birthDate;

    private Long tripStyleId;

    @Enumerated(EnumType.STRING)
    private TripmateCharacterType characterType;

    private String mbti;

    @ColumnDefault("false")
    private boolean deleted;

    public void deleteAccount() {
        this.deleted = true;
    }


    public String getAgeRange() {
        return "30대";
    }

    public void applyTripmatePersonalizedTestResult(TripmatePersonalizedTestResult result) {
        this.changeMBTI(result.mbti());
        this.tripStyleId = result.tripStyleId();
        this.gender = result.gender();
        this.birthDate = result.birthDate();
    }

    private void changeMBTI(String mbti) {
        this.mbti = mbti;
        this.characterType = TripmateCharacterType.fromMBTI(mbti);
    }
}
