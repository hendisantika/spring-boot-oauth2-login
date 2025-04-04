package id.my.hendisantika.oauth2login.service;

import id.my.hendisantika.oauth2login.entity.User;
import id.my.hendisantika.oauth2login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-oauth2-login
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 19/03/25
 * Time: 05.28
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public boolean userExists(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User user = userOptional.get();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getName(), new ArrayList<>());
    }
}
