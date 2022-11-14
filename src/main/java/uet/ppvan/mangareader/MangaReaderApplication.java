package uet.ppvan.mangareader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RestController
public class MangaReaderApplication {

    @GetMapping("/")
    public String index() {
        return "<h1>Hello world</h1>";
    }

    public static void main(String[] args) {
        SpringApplication.run(MangaReaderApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

//    @Bean
//    @Autowired
//    public CommandLineRunner init(GenreRepository genreRepository) {
//        return args -> {
//            for (var genre: Genre.values()) {
//                var dbGenre = new GenreEntity();
//                dbGenre.setGenre(genre);
//                genreRepository.save(dbGenre);
//            }
//        };
//    }
}
