package uet.ppvan.mangareader.chapters;

import uet.ppvan.mangareader.exceptions.NoSuchElementFound;

public class ChapterNotFound {
    public static NoSuchElementFound withId(Integer id) {
        return new NoSuchElementFound(String.format("Chapter[id=%s] not found.", id));
    }
}
