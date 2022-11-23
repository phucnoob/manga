package uet.ppvan.mangareader.services.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uet.ppvan.mangareader.dtos.ChapterOverview;
import uet.ppvan.mangareader.dtos.MangaDetails;
import uet.ppvan.mangareader.dtos.MangaOverview;
import uet.ppvan.mangareader.enums.Genre;
import uet.ppvan.mangareader.enums.Status;
import uet.ppvan.mangareader.exceptions.ResourceNotFound;
import uet.ppvan.mangareader.mappers.MangaMapper;
import uet.ppvan.mangareader.repositories.ChapterRepository;
import uet.ppvan.mangareader.repositories.GenreRepository;
import uet.ppvan.mangareader.repositories.MangaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

//@Profile("test")
@ExtendWith(MockitoExtension.class)
class MangaServiceImplTest {

    @Mock
    private MangaRepository mangaRepository;
    @Mock
    private GenreRepository genreRepository;

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private MangaMapper mangaMapper;

    @InjectMocks
    private MangaServiceImpl mangaService;


    @Test
    @DisplayName("Throw exception when id is null")
    void shouldNotAcceptNull() {
        when(mangaRepository.findMangaById(null)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> mangaService.getMangaById(null))
            .isInstanceOf(ResourceNotFound.class);
    }

    @Test
    @DisplayName("Throw exception when id not found")
    void shouldThrowIfNotfound() {
        when(mangaRepository.findMangaById(null)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> mangaService.getMangaById(null))
            .isInstanceOf(ResourceNotFound.class);
    }

    @Test
    @DisplayName("should get existed manga id")
    void shouldGetMangaByIdInNotfoundCase() {
        int id = 123;
        List<Genre> genres = List.of(Genre.ADVENTURE, Genre.ANIME, Genre.GAME);
        List<ChapterOverview> chapterOverviews = new ArrayList<>();
        chapterOverviews.add(new ChapterOverview(1, "Test chap", LocalDate.EPOCH));
        MangaDetails details = new MangaDetails(
            "name",
            "cover",
            "description",
            "author",
            genres,
            Status.COMPLETED,
            LocalDateTime.MAX,
            chapterOverviews
        );

        FakeTuple tuple = new FakeTuple();

        when(chapterRepository.findByManga_Id(anyInt())).thenReturn(chapterOverviews);
        when(genreRepository.findGenreEntitiesByMangaId(id)).thenReturn(genres);
        when(mangaRepository.findMangaById(id)).thenReturn(Optional.of(tuple));
        when(mangaMapper.buildMangaDetail(tuple, chapterOverviews, genres)).thenReturn(details);

        Assertions.assertThat(mangaService.getMangaById(id)).isEqualTo(details);
    }


    @Test
    @DisplayName("Get manga overview in exist case.")
    void shouldReturnOverviewWithId() {
        int id = 123;
        var manga = MangaOverview.builder()
                        .id(id)
                        .description("test")
                        .name("ahihi")
                        .cover("cover")
                        .build();

        when(mangaRepository.findOverviewById(id)).thenReturn(Optional.of(manga));

        var actualManga = mangaService.getMangaOverviewById(id);

        Assertions.assertThat(manga).usingRecursiveComparison()
            .isEqualTo(actualManga);
    }

    @Test
    @DisplayName("Get manga overview in not found case.")
    void shouldThrowExceptionWithId() {
        when(mangaRepository.findOverviewById(123)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> mangaService.getMangaOverviewById(123))
            .isInstanceOf(ResourceNotFound.class);
    }
}