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
import uet.ppvan.mangareader.enums.Genre;
import uet.ppvan.mangareader.enums.Status;
import uet.ppvan.mangareader.repositories.RoleRepository;
import uet.ppvan.mangareader.services.impl.FakeTuple;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class MangaMapperTest {

    @Mock
    private RoleRepository roleRepository;


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
    void buildMangaEntity() {
    }
}