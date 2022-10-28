package uet.ppvan.mangareader.chapters;

import java.util.Set;

public record ChapterRequest(
    String name,
    Set<String> images
)
{ }
