package uet.ppvan.mangareader.dto;

import java.time.LocalDate;

public record ChapterRequest(
    String name,
    LocalDate uploadDate)
{ }
