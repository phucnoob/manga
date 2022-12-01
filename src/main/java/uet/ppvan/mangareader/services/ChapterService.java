package uet.ppvan.mangareader.services;

import jakarta.validation.Valid;
import uet.ppvan.mangareader.dtos.ChapterRequest;
import uet.ppvan.mangareader.models.Chapter;


public interface ChapterService {
    Chapter addNewChapter(@Valid ChapterRequest requestData, Integer mangaId);

    Chapter updateChapter(@Valid ChapterRequest request, Integer id);

    void removeChapter(Integer id);

    ChapterRequest getChapter(Integer id);
}
