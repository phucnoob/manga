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
        return args -> {
            Manga manga = new Manga();
            manga.setName("Komi không thể giao tiếp");
            manga.setAuthor("Tomohito Oda");
            manga.setStatus(Status.fromString("Đang tiến hành").get());
            manga.setCover("https://st.ntcdntempv3.com/data/comics/42/komi-khong-the-giao-tiep.jpg");
            manga.setDescription("Một em dở giao tiếp, nhưng 1 khi đã nói thì.......");
            manga.setOtherName("Komi-san can't speak well.; Komi-san has a communication disease.; Komi-san has poor communication skills.; Komi-san is asocial.; Komi-san is socially ill");
            manga.setGenre("Comic");

            Chapter chapter = new Chapter();
            chapter.setManga(manga);
            chapter.setUploadDate(LocalDate.now());
            chapter.setName("Chap 371");

            mangaRepository.save(manga);
            chapterRepository.save(chapter);
        };
    }

}
