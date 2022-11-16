package uet.ppvan.mangareader.dtos;

import lombok.Builder;

@Builder
public record MangaOverview(Integer id, String name, String cover, String description) {
}
