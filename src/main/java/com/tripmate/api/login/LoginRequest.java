package com.tripmate.api.login;

public record LoginRequest(

    String id,
    String nickname,
    String thumbnailImageUrl,
    String profileImageUrl,
    String accessToken,
    String refreshToken

) {}
