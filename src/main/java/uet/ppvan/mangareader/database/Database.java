package uet.ppvan.mangareader.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> System.out.println("Hello, it works");
    }

}
