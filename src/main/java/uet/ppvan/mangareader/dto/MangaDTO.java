package uet.ppvan.mangareader.dto;

import uet.ppvan.mangareader.entities.enums.Status;

public record MangaDTO(
    String name,
    String cover,
    String description,
    String author,
    String otherName,
    Status status
) {
}
