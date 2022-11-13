package uet.ppvan.mangareader.chapters;

import uet.ppvan.mangareader.comons.exceptions.ResourceNotFound;

public class ChapterNotFound {
    public static ResourceNotFound withId(Integer id) {
        return new ResourceNotFound(String.format("Chapter[id=%s] not found.", id));
    }
}
