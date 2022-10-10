package uet.ppvan.mangareader.dto;

public record ObjectResponse
    (
        String status,
        String message,
        Object data
    ) {
    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
}
