package com.tripmate.api.domain;

import lombok.Getter;

@Getter
public enum Feedback {

    P1("매너가 좋았어요"),
    P2("약속시간을 잘 지켰어요"),
    P3("대화가 편했어요"),
    P4("의견을 존중해줬어요"),
    P5("소통이 원활했어요"),
    P6("상황 대처능력이 좋았어요"),
    N1("배려가 부족했어요"),
    N2("약속시간을 지키지 않았어요"),
    N3("수다스러웠어요"),
    N4("농담이 지나쳤어요"),
    N5("정해진 일정을 지키지 않았어요"),
    N6("자기 주장이 강했어요"),
    N7("소통이 어려웠어요");

    private final String description;

    Feedback(String description) {
        this.description = description;
    }

}
