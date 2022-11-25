package uet.ppvan.mangareader.services;

import uet.ppvan.mangareader.dtos.ChapterOverview;
import uet.ppvan.mangareader.dtos.MangaDetails;
import uet.ppvan.mangareader.dtos.MangaOverview;
import uet.ppvan.mangareader.dtos.MangaRequest;
import uet.ppvan.mangareader.models.Manga;

import javax.validation.Valid;
import java.util.List;

public interface MangaService {
    Integer addNewManga(@Valid MangaRequest requestData);

    Manga updateManga(Integer id,@Valid MangaRequest requestData);

    void deleteManga(Integer id);


    @Deprecated
    List<MangaRequest> getAll(int page, int size);

    List<MangaOverview> getAllOverview(int page, int size);

    MangaDetails getMangaById(Integer id);

    MangaOverview getMangaOverviewById(Integer id);

    /**
     * @param id
     * @return
     * @see #getMangaById(Integer)
     * @deprecated Chapters info already in MangaDetails.
     */
    @Deprecated
    List<ChapterOverview> getAllChapters(Integer id);
}
