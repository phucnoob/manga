package uet.ppvan.mangareader.dtos;

import java.util.List;

public record ErrorResponse(
    String message,
    List<String> details
) {}
