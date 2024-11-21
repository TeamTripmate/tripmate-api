package com.tripmate.api.login.v2;

import com.tripmate.api.login.LoginJwtInputDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * access token, refresh token 자체 제작 version
 */
@Component
public class JwtTokenProviderV2 {

    private static final int MIN_SECRET_KEY_LENGTH = 256;
    private final Key key;
    private final long accessTokenExpTime;
    private final long refreshTokenExpTime;

    public JwtTokenProviderV2(
        @Value("${jwt.key}") String secretKey,
        @Value("${jwt.access_token_expiration}") long accessTokenExpTime,
        @Value("${jwt.refresh_token_expiration}") long refreshTokenExpTime) {
        validateSecretKey(secretKey);
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
        this.refreshTokenExpTime = refreshTokenExpTime;
    }

    private void validateSecretKey(String secretKey) {

        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length * 8 < MIN_SECRET_KEY_LENGTH) {
            throw new IllegalArgumentException("시크릿 키가 너무 짧습니다. 재설정해주세요!");
        }

    }

    /**
     * access token 생성 메서드
     */
    public String createAccessToken(LoginJwtInputDto inputDto) {

        Map<String, String> claims = new HashMap<>();

        claims.put("id", String.valueOf(inputDto.getId()));
        claims.put("nickname", inputDto.getNickname());
        claims.put("thumbnailImageUrl", inputDto.getThumbnailImageUrl());
        claims.put("profileImageUrl", inputDto.getProfileImageUrl());

        Date now = new Date();

        return Jwts.builder()
            .claims(claims)
            .issuedAt(now)
            .expiration(new Date(now.getTime() + accessTokenExpTime))
            .signWith(key)
            .compact();
    }

    /**
     * access token 갱신 메서드
     */
    public String refreshAccessToken(Claims claims) {

        Date now = new Date();

        return Jwts.builder()
            .claims(claims)
            .issuedAt(now)
            .expiration(new Date(now.getTime() + accessTokenExpTime))
            .signWith(key)
            .compact();
    }

    /**
     * refresh token 생성 메서드
     */
    public String createRefreshToken(Long userId) {

        Map<String, String> claims = new HashMap<>();
        claims.put("id", String.valueOf(userId));

        Date now = new Date();

        return Jwts.builder()
            .claims(claims)
            .issuedAt(now)
            .expiration(new Date(now.getTime() + refreshTokenExpTime))
            .signWith(key)
            .compact();
    }

    /**
     * 토큰 유효성 확인
     */
    public boolean validateToken(String token) {
        Claims claims = Jwts.parser()
            .verifyWith((SecretKey) key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
        return !claims.getExpiration().before(new Date());
    }

    /**
     * 토큰 정보 확인
     */
    public Claims getInfoFromToken(String token) {

        try {
            return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
