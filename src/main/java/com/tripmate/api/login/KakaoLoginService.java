package com.tripmate.api.login;


import com.tripmate.api.entity.UserEntity;
import com.tripmate.api.entity.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;


@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoLoginService {


    @Value("${REST_API_KEY}")
    private String clientId;
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String KAUTH_TOKEN_URL_HOST;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String REDIRECT_URI;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 인가코드로 엑세스토큰 받아오는 메서드
     */
    public String getAccessTokenFromKakao(String code) {

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

        UserEntity user = getUserInfoFromKakao(kakaoTokenResponseDto.getAccessToken());

        LoginJwtInputDto loginJwtInputDto = LoginJwtInputDto.builder()
            .id(user.getKakaoId())
            .profileImageUrl(user.getProfileImage())
            .thumbnailImageUrl(user.getThumbnailImage())
            .nickname(user.getNickname())
            .build();

        String token = jwtTokenProvider.createToken(loginJwtInputDto);
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
     *
     */
    public UserEntity doKakaoAutoLogin(KakaoUserInfoResponseDto responseDto) {

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
     *
     */
    public String doKakaoAutoLoginV2(LoginRequest request) {

        UserEntity user = saveUser(request);

        LoginJwtInputDto loginJwtInputDto = LoginJwtInputDto.builder()
            .id(user.getKakaoId())
            .profileImageUrl(user.getProfileImage())
            .thumbnailImageUrl(user.getThumbnailImage())
            .nickname(user.getProfileImage())
            .accessToken(request.accessToken())
            .build();

        return jwtTokenProvider.createToken(loginJwtInputDto);
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

}
