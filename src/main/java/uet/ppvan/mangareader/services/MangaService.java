package uet.ppvan.mangareader.services;

import uet.ppvan.mangareader.dtos.ChapterOverview;
import uet.ppvan.mangareader.dtos.MangaDetails;
import uet.ppvan.mangareader.dtos.MangaOverview;
import uet.ppvan.mangareader.dtos.MangaRequest;

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
