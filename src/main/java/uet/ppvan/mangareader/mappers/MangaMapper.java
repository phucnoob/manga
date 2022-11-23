package uet.ppvan.mangareader.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dtos.ChapterOverview;
import uet.ppvan.mangareader.dtos.MangaDetails;
import uet.ppvan.mangareader.dtos.MangaRequest;
import uet.ppvan.mangareader.enums.Genre;
import uet.ppvan.mangareader.enums.Status;
import uet.ppvan.mangareader.models.GenreEntity;
import uet.ppvan.mangareader.models.Manga;
import uet.ppvan.mangareader.repositories.GenreRepository;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MangaMapper {

    private final GenreRepository genreRepository;

    public MangaDetails buildMangaDetail(Tuple mangaData, List<ChapterOverview> chapters, List<Genre> genres) {

        if (mangaData == null || chapters == null || genres == null) {
            throw new IllegalArgumentException("Null data is not accepted");
        }

        return new MangaDetails(
            mangaData.get("name", String.class),
            mangaData.get("cover", String.class),
            mangaData.get("description", String.class),
            mangaData.get("author", String.class),
            genres,
            mangaData.get("status", Status.class),
            mangaData.get("last_update", LocalDateTime.class),
            chapters
        );
    }

    public MangaRequest buildMangaRequest(Manga manga) {
        return new MangaRequest(
            manga.getName(),
            manga.getCover(),
            manga.getDescription(),
            manga.getAuthor(),
            manga.getOtherName(),
            manga.getStatus(),
            manga.getGenres().stream().map(GenreEntity::getGenre).collect(Collectors.toSet())
        );
    }

    public Manga buildMangaEntity(MangaRequest mangaRequest) {
        Manga manga = new Manga();
        manga.setName(mangaRequest.name());
        manga.setAuthor(mangaRequest.author());
        manga.setDescription(mangaRequest.description());
        manga.setCover(mangaRequest.cover());
        manga.setStatus(mangaRequest.status());
        manga.setOtherName(mangaRequest.otherName());
        manga.setLastUpdate(LocalDateTime.now(ZoneOffset.UTC));
        var genres = mangaRequest.genres().stream()
                         .map(g -> genreRepository.findGenreEntityByGenre(g))
                         .collect(Collectors.toSet());

        manga.setGenres(genres);

        return manga;
    }
}
