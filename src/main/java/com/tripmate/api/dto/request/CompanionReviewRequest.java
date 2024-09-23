package com.tripmate.api.dto.request;

import java.util.List;

public record CompanionReviewRequest(
    Long companionId,
    List<String> likeList,
    List<String> badList
) {

}
