package uet.ppvan.mangareader.exceptions;

public class ChapterNotFound {
    public static NoSuchElementFound withId(Integer id) {
        return new NoSuchElementFound(String.format("Chapter[id=%s] not found.", id));
    }
}
