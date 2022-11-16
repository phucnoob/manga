package uet.ppvan.mangareader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RestController
@EnableCaching
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
//    public CommandLineRunner init(
//        ProfileRepository profileRepository,
//        UserRepository userRepository,
//        RoleRepository roleRepository) {
//        return args -> {
//           User user = new User();
//           Profile profile = new Profile();
//           profile.setAvatar("Avavter");
//           profile.setCover("Cover");
//           profile.setBio("Hummm");
//
//           user.setUsername("test123");
//           RoleEntity role = roleRepository.findByRole(Role.ROLE_USER);
//           user.setRole(role);
//           user.setEmail("test123@gmail.com");
//           user.setPassword("##pass");
//
//            user = userRepository.save(user);
//            profile.setUser(user);
//            user.setProfile(profile);
////
//            profileRepository.save(profile);
//        };
//    }
}
