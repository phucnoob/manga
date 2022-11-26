package uet.ppvan.mangareader.mappers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uet.ppvan.mangareader.dtos.ChapterRequest;
import uet.ppvan.mangareader.models.Chapter;
import uet.ppvan.mangareader.models.Manga;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ChapterMapperTest {

    @InjectMocks
    private ChapterMapper chapterMapper;

    private Validator validator;

    @BeforeEach
    void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("Validate data with @Valid")
    void shouldValidateProperly() {
        ChapterRequest request = new ChapterRequest(null, List.of("hihi"));
        var requestViolations = validator.validate(request);
        Assertions.assertThat(requestViolations).isNotEmpty();

        Manga manga = new Manga();
        manga.setId(12);
        var violations = validator.validate(manga);
        validator.validate(request);

        Assertions.assertThat(violations).isNotEmpty();
        chapterMapper.buildChapterEntity(request, manga);
    }

    @Test
    @DisplayName("Return exact data in normal case")
    void buildEntityNormal() {
        ChapterRequest request = new ChapterRequest("name", List.of("hihi"));
        Manga manga = new Manga();
        manga.setId(12);
        Chapter ch = new Chapter();
        ch.setId(0);
        ch.setName(request.name());
        ch.setUpdatedDate(LocalDate.EPOCH);
        ch.setManga(manga);
        ch.setServer("");
        ch.setImages(List.of("hihi"));

        Chapter chapter = chapterMapper.buildChapterEntity(request, manga);

        Assertions.assertThat(chapter)
            .usingRecursiveComparison()
            .ignoringFieldsOfTypes(LocalDate.class, LocalDateTime.class)
            .isEqualTo(ch);
    }

    @Test
    @DisplayName("Throw if manga is null or id is null")
    void shouldThrowIfMangaIdNull() {
        ChapterRequest request = new ChapterRequest("name", List.of("hihi"));
        Manga manga = new Manga();
        manga.setId(null);

        Assertions.assertThatThrownBy(() -> chapterMapper.buildChapterEntity(request, manga))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Return expected data")
    void buildChapterDTO() {
        ChapterRequest request = new ChapterRequest("name", List.of("hihi"));
        Manga manga = new Manga();
        manga.setId(12);
        Chapter ch = new Chapter();
        ch.setId(0);
        ch.setName(request.name());
        ch.setUpdatedDate(LocalDate.EPOCH);
        ch.setManga(manga);
        ch.setServer("");
        ch.setImages(List.of("hihi"));


        Assertions.assertThat(chapterMapper.buildChapterDTO(ch))
            .usingRecursiveComparison()
            .ignoringFieldsOfTypes(LocalDate.class)
            .isEqualTo(request);
    }
}