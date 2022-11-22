package uet.ppvan.mangareader.dtos;

import lombok.Builder;
import uet.ppvan.mangareader.enums.Genre;
import uet.ppvan.mangareader.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * DTO of {@link uet.ppvan.mangareader.models.Manga Manga}
 * @param name
 * @param cover Manga cover image link
 * @param description
 * @param author
 * @param genres String representation of {@link Genre}
 * @see uet.ppvan.mangareader.converters.GenreConverter
 * @param status String representation of {@link Status}
 * @see uet.ppvan.mangareader.converters.StatusConverter
 * @param lastUpdate
 * @param chapters
 */

@Builder
public record MangaDetails(
String name,
String cover,
String description,
String author,
Set<Genre> genres,
Status status,
LocalDateTime lastUpdate,
List<ChapterOverview> chapters
) {
}
