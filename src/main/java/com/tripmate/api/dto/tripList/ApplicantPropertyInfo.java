package com.tripmate.api.dto.tripList;

import com.tripmate.api.dto.response.MyCollectCompanionListResponse.ApplicantInfo;
import java.util.ArrayList;
import java.util.List;
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
public class ApplicantPropertyInfo {

    private Long userId;

    private String keyword1;
    private String keyword2;
    private String keyword3;

    private String tripStyle;
    private String characterId;

    public List<String> getKeywordList() {
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add(this.keyword1);
        keywords.add(this.keyword2);
        keywords.add(this.keyword3);

        return keywords;
    }

    public ApplicantInfo getApplicantInfoBuild() {
        return ApplicantInfo.builder()
            .userId(this.userId)
            .tripStyle(this.tripStyle)
            .characterId(this.characterId)
            .selectedKeyword(getKeywordList()).build();
    }
}
