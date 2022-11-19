package uet.ppvan.mangareader.services.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uet.ppvan.mangareader.dtos.MangaDetails;
import uet.ppvan.mangareader.dtos.MangaOverview;
import uet.ppvan.mangareader.enums.Genre;
import uet.ppvan.mangareader.exceptions.ResourceNotFound;
import uet.ppvan.mangareader.repositories.ChapterRepository;
import uet.ppvan.mangareader.repositories.GenreRepository;
import uet.ppvan.mangareader.repositories.MangaRepository;
import uet.ppvan.mangareader.services.MangaService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class MangaServiceImplTest {

    @Mock
    private MangaRepository mangaRepository;
    @Mock
    private GenreRepository genreRepository;

    @Mock
    private ChapterRepository chapterRepository;

    @Test
    @DisplayName("should get existed manga id")
    void shouldGetMangaById() {
        MangaService mangaService = new MangaServiceImpl(mangaRepository, genreRepository, chapterRepository);
        MangaDetails mangaDetails = MangaDetails.builder()
        .name("A Story About a Man and a Woman and When They Sleep Together, Money Appears Out of Nowhere")
        .author("Tokiwa")
        .lastUpdate(LocalDateTime.now())
        .genres(Set.of(Genre.ROMANCE, Genre.MANGA))
        .chapters(Collections.emptyList())
        .build();

        Mockito.when(mangaRepository.findMangaById(123)).thenReturn(Optional.of(mangaDetails));

        MangaDetails actualManga = mangaService.getMangaById(123);

        Assertions.assertThat(actualManga).isEqualTo(mangaDetails);
    }

    @Test
    @DisplayName("should get existed manga id")
    void shouldGetMangaByIdInNotfoundCase() {
        MangaService mangaService = new MangaServiceImpl(mangaRepository, genreRepository, chapterRepository);
        Mockito.when(mangaRepository.findMangaById(123)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> mangaService.getMangaById(123)).isInstanceOf(ResourceNotFound.class);
    }


    @Test
    @DisplayName("Get manga overview in exist case.")
    void shouldReturnOverviewWithId() {
        MangaService mangaService = new MangaServiceImpl(mangaRepository, genreRepository, chapterRepository);

        var manga = MangaOverview.builder()
        .id(123)
        .description("test")
        .name("ahihi")
        .cover("cover")
        .build();

        Mockito.when(mangaRepository.findOverviewById(123)).thenReturn(Optional.of(manga));

        var actualManga = mangaService.getMangaOverviewById(123);

        Assertions.assertThat(manga).usingRecursiveComparison()
        .isEqualTo(actualManga);
    }

    @Test
    @DisplayName("Get manga overview in not found case.")
    void shouldThrowExceptionWithId() {
        MangaService mangaService = new MangaServiceImpl(mangaRepository, genreRepository, chapterRepository);
        Mockito.when(mangaRepository.findOverviewById(123)).thenReturn(Optional.empty());

//        var actualManga = mangaService.getMangaOverviewById(123);

        Assertions.assertThatThrownBy(() -> mangaService.getMangaOverviewById(123))
        .isInstanceOf(ResourceNotFound.class);
    }
}