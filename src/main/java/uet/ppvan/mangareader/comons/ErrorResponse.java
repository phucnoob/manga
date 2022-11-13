package uet.ppvan.mangareader.comons;

import java.util.List;

public record ErrorResponse(
    String message,
    List<String> details
) {}
