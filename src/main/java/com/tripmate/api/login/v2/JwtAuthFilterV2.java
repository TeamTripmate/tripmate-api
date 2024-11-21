package com.tripmate.api.login.v2;

import com.tripmate.api.entity.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilterV2 extends OncePerRequestFilter {

    private final JwtTokenProviderV2 jwtTokenProvider;
    private final UserRepository userRepository;
    private final KakaoLoginServiceV2 kakaoLoginService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        log.info("HERE ==== " + authorizationHeader);
        // access token 헤더에 있는 경우
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                log.info("here == " + token);
                //access token 유효성 검증
                if (jwtTokenProvider.validateToken(token)) {
                    Long userId = Long.valueOf(jwtTokenProvider.getInfoFromToken(token).get("id").toString());
                    if (userRepository.existsById(userId)) {
                        // securityContext에 유저 정보 저장
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userId, null, Collections.emptyList());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        filterChain.doFilter(request, response);
                        return;
                    }
                }
            } catch (ExpiredJwtException e) {

                String refreshToken = kakaoLoginService.updateAccessToken(token);

                log.info("coming exception ==== " + token);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Bearer " + refreshToken);
                response.setHeader("Authorization", "Bearer " + refreshToken);
                return;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized: Invalid or missing JWT token");
            }

        }

        filterChain.doFilter(request, response);
    }
}
