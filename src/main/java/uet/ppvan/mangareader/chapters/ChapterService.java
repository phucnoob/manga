package uet.ppvan.mangareader.chapters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.chapters.image.ImageService;
import uet.ppvan.mangareader.mangas.Manga;
import uet.ppvan.mangareader.mangas.interfaces.MangaRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final MangaRepository mangaRepository;

    private final ImageService imageService;


    public void addNewChapter(ChapterRequest requestData, Integer mangaId) {

        Chapter chapter = mangaRepository.findById(mangaId).map(
            manga -> ChapterService.toChapter(requestData, manga)
        ).orElseThrow();

        chapterRepository.save(chapter);

        imageService.saveImages(requestData.images(), chapter.getId());
    }

    public void updateChapter(ChapterRequest request, Integer id) {
        var founded = chapterRepository.findById(id);
        if (founded.isPresent()) {
            var chapter = founded.get();
            chapter.setName(request.name());
            chapter.setImages(ImageService.toImages(request.images(), chapter));
            chapter.setUpdatedDate(LocalDate.now(ZoneOffset.UTC));

            chapterRepository.save(chapter);
        } else {
            throw ChapterNotFound.withId(id);
        }
    }

    public void removeChapter(Integer id) {
        if (chapterRepository.existsById(id)) {
            chapterRepository.deleteById(id);
        } else {
            throw ChapterNotFound.withId(id);
        }
    }

    public ChapterRequest getChapter(Integer id) {
        return chapterRepository.findById(id)
            .map(ChapterService::toChapterDTO)
                .orElseThrow(() -> ChapterNotFound.withId(id));
    }

//    public List<Image>

    public static Chapter toChapter(ChapterRequest chapterRequest, Manga manga) {
        Chapter chapter = new Chapter();
        chapter.setName(chapterRequest.name());
        chapter.setUpdatedDate(LocalDate.now(ZoneOffset.UTC)); // Auto now
        // TODO Maybe need timezone fix.
        chapter.setManga(manga);

        return chapter;
    }

    public static ChapterRequest toChapterDTO(Chapter chapter) {
        return new ChapterRequest(
            chapter.getName(),
            ImageService.toImageRequest(chapter.getImages())
        );
    }
}
