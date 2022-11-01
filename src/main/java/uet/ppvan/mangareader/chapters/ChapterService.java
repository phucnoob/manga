package uet.ppvan.mangareader.chapters;

import uet.ppvan.mangareader.chapters.ChapterRequest;

public interface ChapterService {
    void addNewChapter(ChapterRequest requestData, Integer mangaId);

    void updateChapter(ChapterRequest request, Integer id);

    void removeChapter(Integer id);

    ChapterRequest getChapter(Integer id);
}
