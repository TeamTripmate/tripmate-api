package com.tripmate.api.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TripmateCharacterType {

    PENGUIN("아기 펭귄"),
    HONEYBEE("아기 꿀벌"),
    ELEPHANT("아기 코끼리"),
    DOLPHIN("아기 돌고래"),
    TURTLE("아기 거북"),
    PANDA("아기 판다");

    private final String name;

    public static TripmateCharacterType fromMBTI(String mbti) {
        if (mbti == null || mbti.length() != 4) {
            throw new IllegalArgumentException("유효하지 않은 MBTI입니다. MBTI 길이는 4자리여야 합니다.");
        }

        char ch1 = mbti.charAt(0);
        char ch4 = mbti.charAt(3);

        String key = "" + ch1 + ch4;

        return switch (key) {
            case "EJ" -> HONEYBEE;  // E + J -> 꿀벌
            case "Ej" -> ELEPHANT;  // E + j -> 코끼리
            case "EP" -> DOLPHIN;   // E + P -> 돌고래
            case "IJ" -> PENGUIN;   // I + J -> 펭귄
            case "Ij" -> TURTLE;    // I + j -> 거북
            case "IP" -> PANDA;     // I + P -> 판다
            default -> throw new IllegalArgumentException("유효하지 않은 MBTI입니다. ('E' / 'I' 및 'J' / 'j' / 'P' 포함 확인)");
        };
    }
}
