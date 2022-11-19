package uet.ppvan.mangareader.dtos;

import lombok.Builder;

/**
 * Simple manga info used in ajax request.
 * @param id
 * @param name
 * @param cover
 * @param description
 */
@Builder
public record MangaOverview(Integer id, String name, String cover, String description) {
}
