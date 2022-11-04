package uet.ppvan.mangareader.mangas;

import uet.ppvan.mangareader.chapters.ChapterOverview;
import uet.ppvan.mangareader.mangas.enums.Genre;
import uet.ppvan.mangareader.mangas.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface MangaDetails {
    String getName();

    String getCover();

    String getDescription();

    String getAuthor();

    Set<GenreView> getGenres();

    Status getStatus();

    LocalDateTime getLastUpdate();

    List<ChapterOverview> getChapters();

    interface GenreView {
        Genre getGenre();
    }
}
