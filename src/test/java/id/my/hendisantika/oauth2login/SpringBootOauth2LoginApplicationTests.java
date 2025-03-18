package id.my.hendisantika.oauth2login;

import id.my.hendisantika.oauth2login.entity.User;
import id.my.hendisantika.oauth2login.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
class SpringBootOauth2LoginApplicationTests {

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:17.4-alpine3.21")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @BeforeEach
    void deleteAll() {
        userRepository.deleteAll();
        initDaTa();
    }

    private void initDaTa() {
        userRepository.saveAll(List.of(
                new User("admin@gmail.com", "admin"),
                new User("hendisantika@gmail.com", "hendisantika")
        ));
    }

    @Test
    void testPostgresIsUpAndRunning() {
        assertTrue(postgresContainer.isCreated());
        assertTrue(postgresContainer.isRunning());
        assertTrue(userRepository.count() > 0);
    }

    @Test
    void checkDataIsExist() {
        assertTrue(userRepository.findByEmail("admin@gmail.com").isPresent());
        assertTrue(userRepository.findByEmail("hendisantika@gmail.com").isPresent());
        assertTrue(userRepository.findByEmail("unknown@gmail.com").isEmpty());
        assertEquals("admin", userRepository.findByEmail("admin@gmail.com").get().getName());
        assertEquals("hendisantika", userRepository.findByEmail("hendisantika@gmail.com").get().getName());
        assertTrue(userRepository.findByEmail("unknown@gmail.com").isEmpty());
    }

}
