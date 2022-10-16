package uet.ppvan.mangareader.chapters;

import uet.ppvan.mangareader.chapters.image.ImageRequest;

import java.util.List;

public record ChapterRequest(
    String name,
    List<ImageRequest> images
)
{ }
