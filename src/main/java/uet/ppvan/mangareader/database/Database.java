package uet.ppvan.mangareader.database;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uet.ppvan.mangareader.entities.Manga;
import uet.ppvan.mangareader.entities.enums.Status;
import uet.ppvan.mangareader.repositories.MangaRepository;

@Configuration
public class Database {

    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    @Bean
    @Autowired
    public CommandLineRunner initDatabase(MangaRepository mangaRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Manga manga1 = new Manga();
                manga1.setOtherName("Teen khac");
                manga1.setName("Teen Titans");
                manga1.setCover("Cover");
                manga1.setAuthor("ABC");
                manga1.setDescription("Ahihi");
                manga1.setStatus(Status.ALL);

                Manga manga2 = new Manga();
                manga2.setOtherName("Teen khac");
                manga2.setName("Teen Titans");
                manga2.setCover("Cover");
                manga2.setAuthor("ABC");
                manga2.setDescription("Ahihi");
                manga2.setStatus(Status.CONTINUE);

                for (int i = 0; i < 10; i++) {
                    Manga manga = new Manga();
                    manga.setOtherName("Ten khac" + i);
                    manga.setName("Teen Titans " + i);
                    manga.setCover("Cover " + i);
                    manga.setAuthor("ABC " + i);
                    manga.setDescription("Ahihi " + i);
                    manga.setStatus(Status.CONTINUE);
                    mangaRepository.save(manga);
                }

                logger.info("Insert data:");
                mangaRepository.saveAll(List.of(manga1, manga2));
            }
        };
    }

}
