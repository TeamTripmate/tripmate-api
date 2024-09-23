package com.tripmate.api.login;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long accessTokenExpTime;

    public JwtTokenProvider(
        @Value("${jwt.key}") String secretKey,
        @Value("${jwt.access_token_expiration}") long accessTokenExpTime) {

        String encode = Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
        byte[] keyBytes = Decoders.BASE64.decode(encode);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
    }

    /**
     * JWT 생성
     */
    public String createToken(LoginJwtInputDto inputDto) {

        HashMap<String, String> claims = new HashMap<>();

        claims.put("id", String.valueOf(inputDto.getId()));
        claims.put("nickname", inputDto.getNickname());
        claims.put("thumbnailImageUrl", inputDto.getThumbnailImageUrl());
        claims.put("profileImageUrl", inputDto.getProfileImageUrl());
        claims.put("accessToken", inputDto.getAccessToken());

        Date now = new Date();

        return Jwts.builder()
            .claims(claims)
            .issuedAt(now)
            .expiration(new Date(now.getTime() + accessTokenExpTime))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * 토큰 유효성 확인
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(key)  // 서명 검증을 위한 키 설정
                .build()
                .parseClaimsJws(token)  // 토큰을 파싱하여 클레임을 가져옴
                .getBody();
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 토큰 정보 확인
     */
    public Claims getInfoFromToken(String token) {

        return Jwts.parser()
            .setSigningKey(key)  // 서명 검증을 위한 키 설정
            .build()
            .parseClaimsJws(token)  // 토큰을 파싱하여 클레임을 가져옴
            .getBody();

    }


}
