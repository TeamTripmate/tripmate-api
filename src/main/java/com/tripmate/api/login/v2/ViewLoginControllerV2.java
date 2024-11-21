package com.tripmate.api.login.v2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/v2")
public class ViewLoginControllerV2 {

    @Value("${KAKAO_REST_API_KEY}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("redirectUri", redirectUri);
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

}
