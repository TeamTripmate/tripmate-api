package com.tripmate.api.login.v2;

import com.tripmate.api.dto.request.WithdrawalRequest;
import com.tripmate.api.login.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class LoginControllerV2 {

    private final KakaoLoginServiceV2 kakaoLoginService;

    @GetMapping("/oauth/check")
    public ResponseEntity<Void> kakaoLogin(@RequestParam("code") String code) {

        log.info(code);

        String token = kakaoLoginService.getJWTFromKakaoLogin(code);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return new ResponseEntity<>(headers, HttpStatus.OK);
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
        summary = "access token 갱신 API",
        description = "헤더로 받아서 헤더로 응답 // request body로 받고, 주는 것도 괜찮을 것 같음"
    )
    @PostMapping("/auth/refresh")
    public ResponseEntity<Void> refreshAuth(@Valid @RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            log.info("here == " + token);
            String refreshedToken = kakaoLoginService.updateAccessToken(token);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + refreshedToken);

            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
        summary = "AuthFilter 테스트용 API",
        description = ""
    )
    @GetMapping("/mypage")
    public ResponseEntity<Void> getMyPage() {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
