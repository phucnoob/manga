package uet.ppvan.mangareader.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO to post new {@link uet.ppvan.mangareader.models.Chapter Chapter}
 * to API. Sent as a {@link org.springframework.web.bind.annotation.RequestBody @RequestBody}
 * @param name Chapter name
 * @param images Chapter images link
 */
public record ChapterRequest(
    @NotNull
    String name,
    @NotEmpty(message = "Chapter must have at least 1 images.")
    List<String> images
)
{ }
