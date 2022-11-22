package uet.ppvan.mangareader.dtos;

import java.util.List;

/**
 * Error Info DTO.
 * @see uet.ppvan.mangareader.advices.ResponseAdvice
 * @param message
 * @param details
 */
public record ErrorResponse(
    String message,
    List<String> details
) {}
