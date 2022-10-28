package uet.ppvan.mangareader.comons;

public record SuccessResponse
    (
        Boolean success,
        String message,
        Object data
    ) {
    public static final Boolean SUCCESS = true;
    public static final Boolean FAILED = false;

}