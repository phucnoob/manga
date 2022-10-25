package uet.ppvan.mangareader.chapters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.chapters.interfaces.ChapterRepository;
import uet.ppvan.mangareader.chapters.interfaces.ChapterService;
import uet.ppvan.mangareader.mangas.Manga;
import uet.ppvan.mangareader.mangas.interfaces.MangaRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final MangaRepository mangaRepository;



    @Override
    public void addNewChapter(ChapterRequest requestData, Integer mangaId) {

        Chapter chapter = mangaRepository.findById(mangaId).map(
            manga -> ChapterServiceImpl.toChapter(requestData, manga)
        ).orElseThrow();

        chapterRepository.save(chapter);
    }

    @Override
    public void updateChapter(ChapterRequest request, Integer id) {
        var founded = chapterRepository.findById(id);
        if (founded.isPresent()) {
            var chapter = founded.get();
            chapter.setName(request.name());
            chapter.setImages(request.images());
            chapter.setUpdatedDate(LocalDate.now(ZoneOffset.UTC));

            chapterRepository.save(chapter);
        } else {
            throw ChapterNotFound.withId(id);
        }
    }

    @Override
    public void removeChapter(Integer id) {
        if (chapterRepository.existsById(id)) {
            chapterRepository.deleteById(id);
        } else {
            throw ChapterNotFound.withId(id);
        }
    }

    @Override
    public ChapterRequest getChapter(Integer id) {
        return chapterRepository.findById(id)
            .map(ChapterServiceImpl::toChapterDTO)
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
            chapter.getImages()
        );
    }
}
