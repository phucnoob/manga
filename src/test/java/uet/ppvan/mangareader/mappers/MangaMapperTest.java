package uet.ppvan.mangareader.mappers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uet.ppvan.mangareader.dtos.ChapterOverview;
import uet.ppvan.mangareader.dtos.MangaDetails;
import uet.ppvan.mangareader.dtos.MangaRequest;
import uet.ppvan.mangareader.enums.Genre;
import uet.ppvan.mangareader.enums.Status;
import uet.ppvan.mangareader.models.GenreEntity;
import uet.ppvan.mangareader.models.Manga;
import uet.ppvan.mangareader.repositories.GenreRepository;
import uet.ppvan.mangareader.services.impl.FakeTuple;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MangaMapperTest {

    @Mock
    private GenreRepository genreRepository;


    @InjectMocks
    private MangaMapper mangaMapper;

    @Test
    @DisplayName("Throw exception if params is not valid")
    void shouldThrowIllegalArgumentException() {
        Assertions.assertThatThrownBy(() -> mangaMapper.buildMangaDetail(new FakeTuple(), null, null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Throw exception if tuple does not contains required fields.")
    void shouldThrowIllegalArgumentExceptionIfTupleInvalid() {
        List<Genre> genres = List.of(Genre.ANIME, Genre.ROMANCE);
        List<ChapterOverview> chapterOverviews = List.of(new ChapterOverview(12, "DENO", LocalDate.EPOCH));
        MangaDetails details = new MangaDetails(
            "name",
            "otherName",
            "cover",
            "description",
            "author",
            genres,
            Status.COMPLETED,
            LocalDateTime.MAX,
            chapterOverviews
        );

        FakeTuple tuple = new FakeTuple();


        Assertions.assertThatThrownBy(() -> mangaMapper.buildMangaDetail(
                tuple,
                Collections.emptyList(),
                Collections.emptyList()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Create MangaRequest Normally.")
    void buildMangaRequest() {

        List<Genre> genres = List.of(Genre.ANIME, Genre.ROMANCE);
        List<ChapterOverview> chapterOverviews = List.of(new ChapterOverview(12, "DENO", LocalDate.EPOCH));
        MangaDetails details = new MangaDetails(
            "name",
            "otherName",
            "cover",
            "description",
            "author",
            genres,
            Status.COMPLETED,
            LocalDateTime.MAX,
            chapterOverviews
        );

        FakeTuple tuple = new FakeTuple(details);

        Assertions.assertThat(mangaMapper.buildMangaDetail(tuple, chapterOverviews, genres))
            .isEqualTo(details);

    }

    @Test
    @DisplayName("Throw exception if request is null")
    void shouldCheckForNullArgument() {
        MangaRequest mangaRequest = new MangaRequest(
            "name",
            "cover",
            "description",
            "author",
            "otherName",
            null,
            Set.of(Genre.DETECTIVE, Genre.SLICE_OF_LIFE)
        );

        Assertions.assertThatThrownBy(() -> mangaMapper.buildMangaEntity(null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Return valid data in normal case")
    void shouldCovertNormal() {
        MangaRequest mangaRequest = new MangaRequest(
            "name",
            "cover",
            "description",
            "author",
            "otherName",
            Status.COMPLETED,
            Set.of(Genre.DETECTIVE, Genre.SLICE_OF_LIFE)
        );
        GenreEntity entity = new GenreEntity();
        entity.setId(1);
        entity.setGenre(Genre.DETECTIVE);

        Manga manga = new Manga();
        manga.setId(0);
        manga.setName(mangaRequest.name());
        manga.setCover(mangaRequest.cover());
        manga.setDescription(mangaRequest.description());
        manga.setAuthor(mangaRequest.author());
        manga.setGenres(Set.of(entity));
        manga.setOtherName(mangaRequest.otherName());
        manga.setStatus(Status.COMPLETED);
        manga.setLastUpdate(LocalDateTime.now());
        manga.setChapters(Collections.emptyList());


        when(genreRepository.findGenreEntityByGenre(any(Genre.class))).thenReturn(entity);

        Assertions.assertThat(mangaMapper.buildMangaEntity(mangaRequest))
            .usingRecursiveComparison()
            .ignoringFieldsOfTypes(LocalDateTime.class, LocalDate.class)
            .isEqualTo(manga);
    }

    @Test
    @DisplayName("Return valid DTO in normal case")
    void shouldConvertNormal() {
        MangaRequest mangaRequest = new MangaRequest(
            "name",
            "cover",
            "description",
            "author",
            "otherName",
            Status.COMPLETED,
            Set.of(Genre.DETECTIVE)
        );
        GenreEntity entity = new GenreEntity();
        entity.setId(1);
        entity.setGenre(Genre.DETECTIVE);

        Manga manga = new Manga();
        manga.setId(0);
        manga.setName(mangaRequest.name());
        manga.setCover(mangaRequest.cover());
        manga.setDescription(mangaRequest.description());
        manga.setAuthor(mangaRequest.author());
        manga.setGenres(Set.of(entity));
        manga.setOtherName(mangaRequest.otherName());
        manga.setStatus(Status.COMPLETED);
        manga.setLastUpdate(LocalDateTime.now());
        manga.setChapters(Collections.emptyList());


        Assertions.assertThat(mangaMapper.buildMangaRequest(manga))
            .usingRecursiveComparison()
            .ignoringFieldsOfTypes(LocalDateTime.class, LocalDate.class)
            .isEqualTo(mangaRequest);
    }
}