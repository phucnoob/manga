package uet.ppvan.mangareader.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import uet.ppvan.mangareader.dtos.ChapterOverview;
import uet.ppvan.mangareader.dtos.MangaDetails;
import uet.ppvan.mangareader.dtos.MangaOverview;
import uet.ppvan.mangareader.dtos.MangaRequest;
import uet.ppvan.mangareader.enums.Genre;
import uet.ppvan.mangareader.exceptions.ResourceNotFound;
import uet.ppvan.mangareader.mappers.MangaMapper;
import uet.ppvan.mangareader.models.Manga;
import uet.ppvan.mangareader.repositories.ChapterRepository;
import uet.ppvan.mangareader.repositories.GenreRepository;
import uet.ppvan.mangareader.repositories.MangaRepository;
import uet.ppvan.mangareader.services.MangaService;

import javax.persistence.Tuple;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Slf4j
@Validated
public class MangaServiceImpl implements MangaService {
    private final MangaRepository mangaRepository;
    private final GenreRepository genreRepository;
    private final ChapterRepository chapterRepository;

    private final MangaMapper mangaMapper;

    @Override
    public Integer addNewManga(MangaRequest requestData) {
        Manga manga = mangaMapper.buildMangaEntity(requestData);
        mangaRepository.save(manga);

        return manga.getId();
    }

    @Override
    public Manga updateManga(Integer id, @Valid MangaRequest requestData) {

        if (!mangaRepository.existsById(id)) {
            throw ResourceNotFound.mangaNotFound(id);
        }

        Manga manga = mangaMapper.buildMangaEntity(requestData);
        manga.setId(id);// update

        return mangaRepository.save(manga);
    }

    @Override
    public void deleteManga(Integer id) {
        mangaRepository.deleteById(id);
    }

    @Override
    public List<MangaRequest> getAll(int page, int size) {
        log.debug("MangaService::getAll(page = {}, size = {})", page, size);
        return mangaRepository.findAll(PageRequest.of(page, size,
                Sort.by("lastUpdate").descending()))
                   .map(mangaMapper::buildMangaRequest).getContent();
    }

    @Override
    public List<MangaOverview> getAllOverview(int page, int size) {
        log.debug("MangaService::getAllOverview(page = {}, size = {})", page, size);
        return mangaRepository.findAllOverview(PageRequest.of(page, size,
                Sort.by("lastUpdate").descending()))
            .getContent();
    }

    @Override
    public MangaDetails getMangaById(Integer id) {
        log.debug("MangaService::getMangaById: id = {}", id);
        try {
            Tuple mangaData = mangaRepository.findMangaById(id).orElseThrow();
            List<ChapterOverview> chapters = chapterRepository.findByManga_Id(id);
            List<Genre> genres = genreRepository.findGenreEntitiesByMangaId(id);

            return mangaMapper.buildMangaDetail(mangaData, chapters, genres);

        } catch (NoSuchElementException ex) {
            throw ResourceNotFound.mangaNotFound(id);
        }
    }

    @Override
    public MangaOverview getMangaOverviewById(Integer id) {
        log.debug("MangaService::getMangaOverviewById id = {}", id);
        return mangaRepository.findOverviewById(id)
        .orElseThrow(() -> ResourceNotFound.mangaNotFound(id));
    }

    @Override
    public List<ChapterOverview> getAllChapters(Integer id) {
        return chapterRepository.findByManga_Id(id);
    }

}
