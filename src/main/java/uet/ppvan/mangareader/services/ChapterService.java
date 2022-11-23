package uet.ppvan.mangareader.services;

import uet.ppvan.mangareader.dtos.ChapterRequest;
import uet.ppvan.mangareader.models.Chapter;

public interface ChapterService {
    Chapter addNewChapter(ChapterRequest requestData, Integer mangaId);

    Chapter updateChapter(ChapterRequest request, Integer id);

    void removeChapter(Integer id);

    ChapterRequest getChapter(Integer id);
}
