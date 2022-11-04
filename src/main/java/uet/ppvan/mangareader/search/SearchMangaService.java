package uet.ppvan.mangareader.search;

import uet.ppvan.mangareader.mangas.MangaOverview;

import java.util.List;

public interface SearchMangaService {
    List<MangaOverview> searchByName(String name);
}
