package com.tripmate.api.domain;

import lombok.Getter;

@Getter
public enum MatchingStatus {

    REQUEST("신청완료"),
    REJECTED("신청거절"),
    CANCELED("모집취소"),
    ACCEPTED("수락완료"),
    ACCOMPANY("동행시작"),
    FINISHED("동행종료"),
    HOST("호스트");

    private final String description;

    MatchingStatus(String description) {
        this.description = description;
    }

}
