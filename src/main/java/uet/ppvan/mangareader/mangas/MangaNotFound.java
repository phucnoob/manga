package uet.ppvan.mangareader.mangas;

import uet.ppvan.mangareader.comons.exceptions.ResourceNotFound;

public class MangaNotFound {
    public static ResourceNotFound withId(Integer id) {
        return new ResourceNotFound(String.format("Manga[id=%s] not found.", id));
    }
}
