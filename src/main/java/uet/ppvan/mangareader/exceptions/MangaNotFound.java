package uet.ppvan.mangareader.exceptions;

public class MangaNotFound {
    public static ResourceNotFound withId(Integer id) {
        return new ResourceNotFound(String.format("Manga[id=%s] not found.", id));
    }
}
