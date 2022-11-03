package uet.ppvan.mangareader.mangas.interfaces;

import uet.ppvan.mangareader.chapters.Chapter;
import uet.ppvan.mangareader.chapters.ChapterOverview;
import uet.ppvan.mangareader.mangas.MangaOverview;
import uet.ppvan.mangareader.mangas.MangaRequest;

import java.util.List;

public interface MangaService {
    Integer addNewManga(MangaRequest requestData);

    void updateManga(Integer id, MangaRequest requestData);

    void deleteManga(Integer id);


    List<MangaRequest> getAll(int page, int size);
    List<MangaOverview> getAllOverview(int page, int size);

    MangaRequest getMangaById(Integer id);

    MangaOverview getMangaOverviewById(Integer id);

    List<ChapterOverview> getAllChapters(Integer id);
}
