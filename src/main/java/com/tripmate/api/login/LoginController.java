package com.tripmate.api.login;

import com.tripmate.api.domain.user.TripmateCharacterType;
import com.tripmate.api.dto.request.TripmatePersonalizedTestRequest;
import com.tripmate.api.dto.request.WithdrawalRequest;
import com.tripmate.api.dto.response.MypageUserInfoResponse;
import com.tripmate.api.dto.response.TripmateApiResponse;
import com.tripmate.api.dto.response.TripmatePersonalizedTestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private final KakaoLoginService kakaoLoginService;

//    @GetMapping("/oauth/check")
    public ResponseEntity<?> kakaoLoginGet(@RequestParam("code") String code) {

        String token = kakaoLoginService.getAccessTokenFromKakao(code);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        LoginResponseDto responseDto = LoginResponseDto.builder()
            .token(token).build();

        return new ResponseEntity<>(responseDto, headers, HttpStatus.OK);
    }


    @Operation(
        summary = "로그인 및 자동 회원가입 API",
        description = ""
    )
    @PostMapping("/login")
    public ResponseEntity<Void> kakaoLogin(@Valid @RequestBody LoginRequest loginRequest) {

        String token = kakaoLoginService.doKakaoAutoLoginV2(loginRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();
    }

    @Operation(
        summary = "회원탈퇴 API",
        description = ""
    )
    @PostMapping("/user/withdrawal")
    public ResponseEntity<Void> userWithdrawal(@Valid @RequestBody WithdrawalRequest withdrawalRequest) {

        kakaoLoginService.doWithdrawal(withdrawalRequest.id());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
        summary = "마이페이지 - 유저 정보 확인 API"
    )
    @GetMapping("user/{userId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TripmateApiResponse<MypageUserInfoResponse>> getUserInfo(@PathVariable("userId") Long userId) {

        kakaoLoginService.getMypageUserInfo(userId);

        String[] array = {"맛집탐험형", "액티비티형", "쇼핑형"};
        List<String> keywordList = new ArrayList<>(Arrays.asList(array));

        return ResponseEntity.ok(TripmateApiResponse.success(
            new MypageUserInfoResponse(keywordList,"PENGUIN","여행가 아기꿀벌","카닉","이미지썸네일URL", "이미지URL")
        ));
    }

    @Operation(
            summary = "Tripmate 개인화 설문(여행 스타일 및 호구조사) API"
    )
    @PostMapping("user/{userId}/personalized-tests")
    public ResponseEntity<TripmateApiResponse<TripmatePersonalizedTestResponse>> submitPersonalizedTest(
            @PathVariable("userId") Long userId,
            @Valid @RequestBody TripmatePersonalizedTestRequest tripmatePersonalizedTestRequest
    ) {
        String mbti = tripmatePersonalizedTestRequest.mbti();
        TripmateCharacterType characterType = TripmateCharacterType.fromMBTI(mbti);
        return ResponseEntity.ok(TripmateApiResponse.success(
                new TripmatePersonalizedTestResponse(characterType)
        ));
    }
}