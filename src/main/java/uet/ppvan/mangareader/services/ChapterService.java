package uet.ppvan.mangareader.services;

import uet.ppvan.mangareader.dtos.ChapterRequest;
import uet.ppvan.mangareader.models.Chapter;

import javax.validation.Valid;
public interface ChapterService {
    Chapter addNewChapter(@Valid ChapterRequest requestData, Integer mangaId);

    Chapter updateChapter(@Valid ChapterRequest request, Integer id);

    void removeChapter(Integer id);

    ChapterRequest getChapter(Integer id);
}
