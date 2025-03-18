package id.my.hendisantika.oauth2login.controller;

import id.my.hendisantika.oauth2login.entity.User;
import id.my.hendisantika.oauth2login.repository.UserRepository;
import id.my.hendisantika.oauth2login.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-oauth2-login
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 19/03/25
 * Time: 05.33
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/callback")
    public String handleOAuth2Callback(OAuth2AuthenticationToken authentication) {
        OAuth2User oAuth2User = authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            // Generate JWT Token
            String token = jwtUtil.generateToken(email);
            return "{\"status\": \"success\", \"token\": \"" + token + "\"}";
        } else {
            return "{\"status\": \"error\", \"message\": \"User not found in database\"}";
        }
    }

}
