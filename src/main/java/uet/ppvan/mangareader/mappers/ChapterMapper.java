package uet.ppvan.mangareader.mappers;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import uet.ppvan.mangareader.dtos.ChapterRequest;
import uet.ppvan.mangareader.models.Chapter;
import uet.ppvan.mangareader.models.Manga;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
@Validated
public class ChapterMapper {
    public Chapter buildChapterEntity(@Valid ChapterRequest chapterRequest, Manga manga) {

        if (manga == null || manga.getId() == null) {
            throw new IllegalArgumentException("Manga can't be null");
        }

        Chapter chapter = new Chapter();
        chapter.setName(chapterRequest.name());
        chapter.setUpdatedDate(LocalDate.now(ZoneOffset.UTC)); // Auto now
        // TODO Maybe need timezone fix.
        chapter.setManga(manga);
        chapter.setServer("");
        chapter.setId(0);
        chapter.setImages(chapterRequest.images());

        return chapter;
    }

    public ChapterRequest buildChapterDTO(@Valid Chapter chapter) {
        return new ChapterRequest(
            chapter.getName(),
            chapter.getImages()
        );
    }
}
