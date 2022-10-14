package uet.ppvan.mangareader.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dto.ChapterRequest;
import uet.ppvan.mangareader.entities.Chapter;
import uet.ppvan.mangareader.entities.Manga;
import uet.ppvan.mangareader.repositories.ChapterRepository;
import uet.ppvan.mangareader.repositories.MangaRepository;

@Service
@AllArgsConstructor
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final MangaRepository mangaRepository;

    private final MangaService mangaService;

    public void addNewChapter(ChapterRequest requestData, Integer mangaId) {
        Chapter chapter = mangaRepository.findById(mangaId).map(
            manga -> ChapterService.toChapter(requestData, manga)
        ).orElseThrow();

        chapterRepository.save(chapter);
    }

    public void removeChapter(Integer id) {
        chapterRepository.deleteById(id);
    }

    public ChapterRequest getChapter(Integer id) {
        return chapterRepository.findById(id)
            .map(ChapterService::toChapterDTO).orElseThrow();
    }

    public static Chapter toChapter(ChapterRequest chapterRequest, Manga manga) {
        Chapter chapter = new Chapter();
        chapter.setName(chapterRequest.name());
        chapter.setUploadDate(chapterRequest.uploadDate());
        chapter.setManga(manga);

        return chapter;
    }

    public static ChapterRequest toChapterDTO(Chapter chapter) {
        return new ChapterRequest(
            chapter.getName(),
            chapter.getUploadDate()
        );
    }
}
