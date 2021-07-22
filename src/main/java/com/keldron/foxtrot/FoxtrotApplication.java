package com.keldron.foxtrot;

import com.keldron.foxtrot.model.AuthorityRole;
import com.keldron.foxtrot.model.User;
import com.keldron.foxtrot.repositories.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;
import java.util.UUID;

@SpringBootApplication
public class FoxtrotApplication {

    private static final Logger log = LoggerFactory.getLogger(FoxtrotApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FoxtrotApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository repository) {
        String password = UUID.randomUUID().toString().replace("-", "");
        log.info("GENERATED PASSWORD FOR MASTER ADMIN PROFILE: " + password);
        final String encodedPassword = Utils.encodePassword(password);
        return (args) -> {
            repository.save(new User("admin", encodedPassword, "admin", "admin", Set.of(AuthorityRole.ADMIN, AuthorityRole.USER)));
        };
    }
}

