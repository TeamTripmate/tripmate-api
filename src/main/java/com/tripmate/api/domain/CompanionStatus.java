package com.tripmate.api.domain;

import lombok.Getter;

@Getter
public enum CompanionStatus {

    RECRUITING("모집중"),
    CANCELED("모집취소"),
    MATCHED("매칭완료"),
    ACCOMPANY("동행시작"),
    FINISHED("동행종료");

    private final String description;

    CompanionStatus(String description) {
        this.description = description;
    }

    public boolean isValidName(String name) {

        for (CompanionStatus status : CompanionStatus.values()) {
            if (status.name().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
}