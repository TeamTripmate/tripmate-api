package com.tripmate.api.domain;

import lombok.Getter;

@Getter
public enum CompanionStatus {

    REQUEST("신청완료"),
    REJECTED("신청거절"),
    ACCEPTED("수락완료"),
    ACCOMPANY("동행시작"),
    FINISHED("동행종료"),
    HOST("호스트");

    private final String description;

    CompanionStatus(String description) {
        this.description = description;
    }

}
