package com.tripmate.api.entity;

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

    private String gender;

    private String birthYear;

    private Long tripStyleId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "trip_style_id", referencedColumnName = "id")
//    private TripStyleEntity tripStyle;

    private String characterType;

    @ColumnDefault("false")
    private boolean deleted;

    public void deleteAccount() {
        this.deleted = true;
    }

    public String getAgeRange() {
        return "30대";
    }
}
