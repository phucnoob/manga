package uet.ppvan.mangareader.dtos;

import lombok.Builder;
import uet.ppvan.mangareader.enums.Genre;
import uet.ppvan.mangareader.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

//public interface MangaDetails {
//    String getName();
//
//    String getCover();
//
//    String getDescription();
//
//    String getAuthor();
//
//    Set<GenreView> getGenres();
//
//    Status getStatus();
//
//    LocalDateTime getLastUpdate();
//
//    List<ChapterOverview> getChapters();
//
//    interface GenreView {
//        Genre getGenre();
//    }
//}

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
