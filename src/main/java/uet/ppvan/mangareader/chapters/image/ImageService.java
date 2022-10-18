package uet.ppvan.mangareader.chapters.image;

import java.util.Set;

public interface ImageService {
    void saveImages(Set<String> imageLinks, Integer chapterId);
}
