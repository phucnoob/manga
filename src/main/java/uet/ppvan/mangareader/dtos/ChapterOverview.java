package uet.ppvan.mangareader.dtos;

import java.time.LocalDate;

public record ChapterOverview(
Integer id,
String name,
LocalDate updatedDate
) {
}
