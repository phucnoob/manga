package uet.ppvan.mangareader.mangas;

import uet.ppvan.mangareader.comons.exceptions.NoSuchElementFound;

public class MangaNotFound {
    public static NoSuchElementFound withId(Integer id) {
        return new NoSuchElementFound(String.format("Manga[id=%s] not found.", id));
    }
}
