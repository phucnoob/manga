package uet.ppvan.mangareader.services;

import uet.ppvan.mangareader.dtos.MangaOverview;

import java.util.List;

public interface SearchMangaService {
    List<MangaOverview> searchByName(String name);
}
