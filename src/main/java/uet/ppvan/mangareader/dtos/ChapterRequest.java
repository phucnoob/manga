package uet.ppvan.mangareader.dtos;

import java.util.Set;

public record ChapterRequest(
    String name,
    Set<String> images
)
{ }
