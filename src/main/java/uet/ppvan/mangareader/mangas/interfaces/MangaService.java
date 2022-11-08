package uet.ppvan.mangareader.mangas.interfaces;

import uet.ppvan.mangareader.chapters.ChapterOverview;
import uet.ppvan.mangareader.mangas.MangaDetails;
import uet.ppvan.mangareader.mangas.MangaOverview;
import uet.ppvan.mangareader.mangas.MangaRequest;

import java.util.List;

public interface MangaService {
    Integer addNewManga(MangaRequest requestData);

    void updateManga(Integer id, MangaRequest requestData);

    void deleteManga(Integer id);


    @Deprecated
    List<MangaRequest> getAll(int page, int size);
    List<MangaOverview> getAllOverview(int page, int size);

    MangaDetails getMangaById(Integer id);

    MangaOverview getMangaOverviewById(Integer id);

    @Deprecated
    List<ChapterOverview> getAllChapters(Integer id);
}
