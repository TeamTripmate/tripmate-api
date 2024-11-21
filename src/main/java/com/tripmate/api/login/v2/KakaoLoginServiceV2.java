package com.tripmate.api.login.v2;


import com.tripmate.api.entity.RefreshTokenEntity;
import com.tripmate.api.entity.RefreshTokenRepository;
import com.tripmate.api.entity.UserEntity;
import com.tripmate.api.entity.UserRepository;
import com.tripmate.api.exception.CustomLoginException;
import com.tripmate.api.login.KakaoTokenResponseDto;
import com.tripmate.api.login.KakaoUserInfoResponseDto;
import com.tripmate.api.login.LoginJwtInputDto;
import com.tripmate.api.login.LoginRequest;
import io.jsonwebtoken.Claims;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoLoginServiceV2 {


    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProviderV2 jwtTokenProvider;
    @Value("${KAKAO_REST_API_KEY}")
    private String clientId;
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String KAUTH_TOKEN_URL_HOST;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String REDIRECT_URI;

    /**
     * 인가코드로 엑세스토큰 받아온 뒤, 유저 정보 받아와서 JWT 만드는 코드
     */
    public String getJWTFromKakaoLogin(String code) {

        // 유저의 카카오 로그인 및 카카오 access token 받아오기
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("redirect_uri", REDIRECT_URI);
        formData.add("code", code);

        KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .bodyToMono(KakaoTokenResponseDto.class)
            .block();

        log.info("access token = " + kakaoTokenResponseDto.getAccessToken());
        // 유저 정보 가져오기
        UserEntity user = getUserInfoFromKakao(kakaoTokenResponseDto.getAccessToken());
        LoginJwtInputDto loginJwtInputDto = LoginJwtInputDto.fromUser(user);

        // 자체 access token 생성
        String token = jwtTokenProvider.createAccessToken(loginJwtInputDto);
        manageRefreshToken(user);

        log.info("token = " + token);

        return token;
    }

    /**
     * 유저 정보 가져오는 메서드
     */
    public UserEntity getUserInfoFromKakao(String accessToken) {

        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

        KakaoUserInfoResponseDto kakaoUserInfoResponseDto = WebClient.create(userInfoUrl).get()
            .headers(httpHeaders -> {
                httpHeaders.add("Authorization", "Bearer " + accessToken);
                httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            })
            .retrieve()
            .bodyToMono(KakaoUserInfoResponseDto.class)
            .block();

        UserEntity userEntity = doKakaoAutoLogin(kakaoUserInfoResponseDto);

        return userEntity;
    }

    /**
     * 자동 로그인 & 회원가입 메서드
     */
    public UserEntity doKakaoAutoLogin(KakaoUserInfoResponseDto responseDto) {

        String kakaoNickname = responseDto.getKakaoNickname();
        String thumbnailImageUrl = responseDto.getThumbnailImageUrl();
        String profileImageUrl = responseDto.getProfileImageUrl();

        log.info("kakaoUserInfoResponseDto = " + responseDto);
        log.info("kakaoNickname = " + kakaoNickname);
        log.info("thumbnailImageUrl = " + thumbnailImageUrl);
        log.info("profileImageUrl = " + profileImageUrl);

        Long kakaoId = responseDto.getId();

        Optional<UserEntity> user = userRepository.findById(kakaoId);

        if (user.isPresent()) {
            return user.get();
        }

        UserEntity userEntity = UserEntity.builder()
            .kakaoId(kakaoId)
            .nickname(responseDto.getKakaoNickname())
            .profileImage(responseDto.getProfileImageUrl())
            .thumbnailImage(responseDto.getThumbnailImageUrl()).build();
        userRepository.save(userEntity);

        return userEntity;
    }

    public UserEntity saveUser(LoginRequest request) {

        Long kakaoId = Long.valueOf(request.id());
        Optional<UserEntity> user = userRepository.findById(kakaoId);

        if (user.isPresent()) {
            return user.get();
        }

        UserEntity userEntity = UserEntity.builder()
            .kakaoId(kakaoId)
            .nickname(request.nickname())
            .profileImage(request.profileImageUrl())
            .thumbnailImage(request.thumbnailImageUrl()).build();
        userRepository.save(userEntity);

        return userEntity;
    }

    /**
     * 자동 로그인 & 회원가입 메서드
     * 카카오로 받은 정보는 최초에만 저장되고 이후로는 카카오 아이디로 사용자 식별만 진행
     * refresh token 만료 혹은 없을 경우, 생성 후 DB 저장
     */
    public String doKakaoAutoLoginV2(LoginRequest request) {

        UserEntity user = saveUser(request);

        LoginJwtInputDto loginJwtInputDto = LoginJwtInputDto.fromUser(user);

        String accessToken = jwtTokenProvider.createAccessToken(loginJwtInputDto);

        manageRefreshToken(user);

        return accessToken;
    }

    /**
     * Refresh token 생성 및 관리 메서드
     * 토큰이 존재하지 않거나 만료 시, 갱신 후 DB 저장
     */
    private void manageRefreshToken(UserEntity user) {
        Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenRepository.findById(user.getKakaoId());

        if (refreshTokenEntity.isEmpty() || !jwtTokenProvider.validateToken(
            refreshTokenEntity.get().getRefreshToken())) {
            String refreshToken = jwtTokenProvider.createRefreshToken(user.getKakaoId());
            RefreshTokenEntity rte = RefreshTokenEntity.builder()
                .userId(user.getKakaoId())
                .refreshToken(refreshToken).build();
            refreshTokenRepository.save(rte);
        }
    }


    /**
     * 회원탈퇴 메서드
     * 활성 유저면 soft delete 진행
     */
    public void doWithdrawal(Long id) {

        UserEntity user = userRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다", null));
        if (user.isDeleted()) {
            throw new NoSuchElementException("존재하지 않는 회원입니다", null);
        }
        user.deleteAccount();

        userRepository.save(user);
    }

    /**
     * Access token 갱신 메서드
     * 1. refresh token 확인
     * - 1) 만료면 재로그인 요구 예외 던지기 - custom exception
     * - 2) 이상 없으면 access token 갱신
     */
    public String updateAccessToken(String token) {
        Claims infoFromToken = jwtTokenProvider.getInfoFromToken(token);

        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findById(
                Long.valueOf(infoFromToken.get("id").toString()))
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 ID입니다"));

        if (jwtTokenProvider.validateToken(refreshTokenEntity.getRefreshToken())) {
            return jwtTokenProvider.refreshAccessToken(infoFromToken);
        }

        throw new CustomLoginException("만료됐습니다. 재로그인해주세요", HttpStatus.UNAUTHORIZED);
    }
}
