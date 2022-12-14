package uet.ppvan.mangareader.dtos;

import org.hibernate.validator.constraints.URL;
import uet.ppvan.mangareader.enums.Genre;
import uet.ppvan.mangareader.enums.Status;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * DTO to post Manga to API as {@link org.springframework.web.bind.annotation.RequestBody @RequestBody}
 * @param name
 * @param cover
 * @param description
 * @param author
 * @param otherName
 * @param status
 * @param genres
 */
public record MangaRequest(
    @NotBlank(message = "Manga name can't be blank.")
    String name,

    @URL(message = "Cover must be an image url.")
    String cover,

    @NotNull(message = "Manga description should not null.")
    String description,

    @NotNull(message = "Author can't be null.")
    String author,

    @NotNull(message = "Other name can't be null.")
    String otherName,

    Status status,

    Set<Genre> genres
) {
}
