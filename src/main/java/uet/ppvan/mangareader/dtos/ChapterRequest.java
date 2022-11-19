package uet.ppvan.mangareader.dtos;

import java.util.Set;

/**
 * DTO to post new {@link uet.ppvan.mangareader.models.Chapter Chapter}
 * to API. Sent as a {@link org.springframework.web.bind.annotation.RequestBody @RequestBody}
 * @param name Chapter name
 * @param images Chapter images link
 */
public record ChapterRequest(
    String name,
    Set<String> images
)
{ }
