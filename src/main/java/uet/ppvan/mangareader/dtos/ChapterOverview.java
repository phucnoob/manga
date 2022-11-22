package uet.ppvan.mangareader.dtos;

import java.time.LocalDate;

/**
 * Simple chapter projection.
 * @param id
 * @param name
 * @param updatedDate
 */
public record ChapterOverview(
Integer id,
String name,
LocalDate updatedDate
) {
}
