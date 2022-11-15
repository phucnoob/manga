package uet.ppvan.mangareader.services;

import uet.ppvan.mangareader.dtos.ChapterRequest;

public interface ChapterService {
    void addNewChapter(ChapterRequest requestData, Integer mangaId);

    void updateChapter(ChapterRequest request, Integer id);

    void removeChapter(Integer id);

    ChapterRequest getChapter(Integer id);
}
