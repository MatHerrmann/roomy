package com.nerdware.roomy.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;

//@Configuration
public class UserConfig {

  //  @Bean
    CommandLineRunner commandLineRunner(UserRepository repository){
        return args -> {
            User matthias = new User(
                    1,
                    "Matthias Herrmann",
                    "mherrmann@gmail.com",
                    true
            );
            User alex = new User(
                    2,
                    "Alexander Otremba",
                    "alkeks@gmail.com",
                    false
            );
            repository.saveAll(List.of(matthias, alex));
        };
    }
}
