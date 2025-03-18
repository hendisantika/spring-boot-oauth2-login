package id.my.hendisantika.oauth2login;

import id.my.hendisantika.oauth2login.entity.User;
import id.my.hendisantika.oauth2login.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringBootOauth2LoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootOauth2LoginApplication.class, args);
    }


    @Bean
    public CommandLineRunner init(UserRepository userRepository) {
        return (args) -> {
            userRepository.deleteAll();
            userRepository.saveAll(
                    List.of(
                            new User("admin@gmail.com", "admin"),
                            new User("hendisantika@gmail.com", "hendisantika")
                    )
            );
        };
    }
}
