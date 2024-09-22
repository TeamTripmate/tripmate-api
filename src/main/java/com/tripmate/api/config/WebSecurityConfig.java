package com.tripmate.api.config;

import com.tripmate.api.entity.UserRepository;
import com.tripmate.api.login.JwtAuthFilter;
import com.tripmate.api.login.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .authorizeHttpRequests(
                        requests -> requests
                                .requestMatchers("/api/v1/**", "/health/**", "/v1/**", "/swagger-ui/**", "/view/**", "/api/**","/css/**", "/error/**").permitAll()
                                .anyRequest().authenticated()
                );

        http.addFilterBefore(new JwtAuthFilter(jwtTokenProvider,userRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
