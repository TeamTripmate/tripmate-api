package com.tripmate.api.dto.tripList;

import java.time.LocalDateTime;

import com.tripmate.api.domain.user.TripmateCharacterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyApplyCompanionListInfo {

    private Long companionId;

    private String title;

    private LocalDateTime date;

    private String openChatLink;

    private boolean reviewYn;

    private String matchingStatus;

    private String keyword1;

    private String keyword2;

    private String keyword3;

    private String tripStyle;

    private TripmateCharacterType characterId;

    @Override
    public String toString() {
        return "MyApplyCompanionListInfo{" +
            "companionId=" + companionId +
            ", title='" + title + '\'' +
            ", date=" + date +
            ", openChatLink='" + openChatLink + '\'' +
            ", reviewYn=" + reviewYn +
            ", matchingStatus='" + matchingStatus + '\'' +
            ", keyword1='" + keyword1 + '\'' +
            ", keyword2='" + keyword2 + '\'' +
            ", keyword3='" + keyword3 + '\'' +
            ", tripStyle='" + tripStyle + '\'' +
            ", characterId='" + characterId + '\'' +
            '}';
    }

}