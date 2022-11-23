package uet.ppvan.mangareader.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dtos.ChapterRequest;
import uet.ppvan.mangareader.exceptions.ResourceNotFound;
import uet.ppvan.mangareader.mappers.ChapterMapper;
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

    private final ChapterMapper chapterMapper;

    @Override
    public Chapter addNewChapter(ChapterRequest requestData, Integer mangaId) {

        if (!mangaRepository.existsById(mangaId)) {
            throw ResourceNotFound.mangaNotFound(mangaId);
        }

        Manga manga = mangaRepository.getReferenceById(mangaId);
        Chapter chapter = chapterMapper.buildChapterEntity(requestData, manga);

        return chapterRepository.save(chapter);
    }

    @Override
    public Chapter updateChapter(ChapterRequest request, Integer id) {
        var founded = chapterRepository.findById(id);
        if (founded.isPresent()) {
            var chapter = founded.get();
            chapter.setName(request.name());
            chapter.setImages(request.images());
            chapter.setUpdatedDate(LocalDate.now(ZoneOffset.UTC));

            return chapterRepository.save(chapter);
        } else {
            throw ResourceNotFound.chapterNotFound(id);
        }
    }

    @Override
    public void removeChapter(Integer id) {
        if (!chapterRepository.existsById(id)) {
            throw ResourceNotFound.chapterNotFound(id);
        }

        chapterRepository.deleteById(id);
    }

    @Override
    public ChapterRequest getChapter(Integer id) {
        return chapterRepository.findById(id)
                   .map(chapterMapper::buildChapterDTO)
                .orElseThrow(() -> ResourceNotFound.chapterNotFound(id));
    }
}
