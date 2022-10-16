package uet.ppvan.mangareader.mangas.interfaces;

import uet.ppvan.mangareader.chapters.Chapter;
import uet.ppvan.mangareader.mangas.MangaRequest;

import java.util.List;

public interface MangaService {
    void addNewManga(MangaRequest requestData);

    void updateManga(Integer id, MangaRequest requestData);

    void deleteManga(Integer id);

    List<MangaRequest> getAll(int page, int size);

    MangaRequest getMangaById(Integer id);

    List<Chapter> getAllChapters(Integer id);
}
