package uet.ppvan.mangareader.dto;

public record ObjectResponse
    (
        String status,
        String message,
        Object data
    ) {
}
