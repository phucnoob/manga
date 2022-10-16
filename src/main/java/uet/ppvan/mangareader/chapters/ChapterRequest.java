package uet.ppvan.mangareader.chapters;

import java.time.LocalDate;

public record ChapterRequest(
    String name,
    LocalDate uploadDate)
{ }
