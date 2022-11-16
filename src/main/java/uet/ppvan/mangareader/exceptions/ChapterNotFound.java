package uet.ppvan.mangareader.exceptions;

public class ChapterNotFound {
    public static ResourceNotFound withId(Integer id) {
        return new ResourceNotFound(String.format("Chapter[id=%s] not found.", id));
    }
}
