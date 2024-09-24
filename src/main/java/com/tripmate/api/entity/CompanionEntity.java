package com.tripmate.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long spotId;  // 여행지 ID
    @NotNull
    private String title;  // 제목
    @NotNull
    private String description;  // 내용
    @NotNull
    private LocalDateTime startDate;  // 동행 시작일
    @NotNull
    private String companionType;  // 동행 유형
    @NotNull
    private String openChatLink;  // 오픈채팅 링크

    @NotNull
    private Long hostId;  // 호스트 ID
    @NotNull
    private boolean sameAgeYn;
    @NotNull
    private boolean sameGenderYn;

}
