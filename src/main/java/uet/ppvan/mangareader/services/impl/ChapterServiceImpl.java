package uet.ppvan.mangareader.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dtos.ChapterRequest;
import uet.ppvan.mangareader.exceptions.ResourceNotFound;
import uet.ppvan.mangareader.models.Chapter;
import uet.ppvan.mangareader.models.Manga;
import uet.ppvan.mangareader.repositories.ChapterRepository;
import uet.ppvan.mangareader.repositories.MangaRepository;
import uet.ppvan.mangareader.services.ChapterService;

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
            throw ResourceNotFound.chapterNotFound(id);
        }
    }

    @Override
    public void removeChapter(Integer id) {
        if (chapterRepository.existsById(id)) {
            chapterRepository.deleteById(id);
        } else {
            throw ResourceNotFound.chapterNotFound(id);
        }
    }

    @Override
    public ChapterRequest getChapter(Integer id) {
        return chapterRepository.findById(id)
            .map(ChapterServiceImpl::toChapterDTO)
                .orElseThrow(() -> ResourceNotFound.chapterNotFound(id));
    }

//    public List<Image>

    public static Chapter toChapter(ChapterRequest chapterRequest, Manga manga) {
        Chapter chapter = new Chapter();
        chapter.setName(chapterRequest.name());
        chapter.setUpdatedDate(LocalDate.now(ZoneOffset.UTC)); // Auto now
        // TODO Maybe need timezone fix.
        chapter.setManga(manga);
        chapter.setImages(chapterRequest.images());

        return chapter;
    }

    public static ChapterRequest toChapterDTO(Chapter chapter) {
        return new ChapterRequest(
            chapter.getName(),
            chapter.getImages()
        );
    }
}
