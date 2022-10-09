package uet.ppvan.mangareader.database;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uet.ppvan.mangareader.entities.Chapter;
import uet.ppvan.mangareader.entities.Manga;
import uet.ppvan.mangareader.entities.enums.Status;
import uet.ppvan.mangareader.repositories.ChapterRepository;
import uet.ppvan.mangareader.repositories.MangaRepository;

@Configuration
public class Database {

    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    @Bean
    @Autowired
    public CommandLineRunner initDatabase(
        MangaRepository mangaRepository,
        ChapterRepository chapterRepository
    ) {
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

                logger.info("Insert data:");
                mangaRepository.saveAll(List.of(manga1, manga2));

                var chap1 = new Chapter();
                chap1.setName("Chapter 1");
                chap1.setManga(manga1);
                chap1.setUploadDate(LocalDate.now());

                var chap2 = new Chapter();
                chap2.setName("Chapter 2");
                chap2.setManga(manga1);
                chap2.setUploadDate(LocalDate.now());

                chapterRepository.saveAll(List.of(chap1, chap2));
            }
        };
    }

}
