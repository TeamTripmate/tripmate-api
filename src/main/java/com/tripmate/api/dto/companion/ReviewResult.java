package com.tripmate.api.dto.companion;

import java.util.List;
import lombok.Builder;

@Builder
public record ReviewResult(

    List<ReviewInfo> reviewInfos,
    List<String> reviewRankList

) {

}
