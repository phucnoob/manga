package uet.ppvan.mangareader.dto;

import java.time.LocalDate;

public record ChapterDTO(
    String name,
    LocalDate uploadDate)
{ }
