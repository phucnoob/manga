package uet.ppvan.mangareader.mappers;

import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dtos.ChapterRequest;
import uet.ppvan.mangareader.models.Chapter;
import uet.ppvan.mangareader.models.Manga;

import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
public class ChapterMapper {
    public Chapter buildChapterEntity(ChapterRequest chapterRequest, Manga manga) {
        Chapter chapter = new Chapter();
        chapter.setName(chapterRequest.name());
        chapter.setUpdatedDate(LocalDate.now(ZoneOffset.UTC)); // Auto now
        // TODO Maybe need timezone fix.
        chapter.setManga(manga);
        chapter.setImages(chapterRequest.images());

        return chapter;
    }

    public ChapterRequest buildChapterDTO(Chapter chapter) {
        return new ChapterRequest(
            chapter.getName(),
            chapter.getImages()
        );
    }
}
