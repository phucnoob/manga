package uet.ppvan.mangareader.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dto.ChapterDTO;
import uet.ppvan.mangareader.entities.Chapter;
import uet.ppvan.mangareader.entities.Manga;
import uet.ppvan.mangareader.repositories.ChapterRepository;
import uet.ppvan.mangareader.repositories.MangaRepository;

@Service
@AllArgsConstructor
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final MangaRepository mangaRepository;

    public void addNewChapter(ChapterDTO requestData, Integer mangaId) {
        Chapter chapter = mangaRepository.findById(mangaId).map(
            manga -> ChapterService.toChapter(requestData, manga)
        ).orElseThrow();

        chapterRepository.save(chapter);
    }

    public void removeChapter(Integer id) {
        chapterRepository.deleteById(id);
    }

    public ChapterDTO getChapter(Integer id) {
        return chapterRepository.findById(id)
            .map(ChapterService::toChapterDTO).orElseThrow();
    }

    public static Chapter toChapter(ChapterDTO chapterDTO, Manga manga) {
        Chapter chapter = new Chapter();
        chapter.setName(chapterDTO.name());
        chapter.setUploadDate(chapterDTO.uploadDate());
        chapter.setManga(manga);

        return chapter;
    }

    public static ChapterDTO toChapterDTO(Chapter chapter) {
        return new ChapterDTO(
            chapter.getName(),
            chapter.getUploadDate()
        );
    }
}
