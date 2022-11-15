package uet.ppvan.mangareader.dtos;

public record SuccessResponse
    (
        Boolean success,
        String message,
        Object data
    ) {
    public static final Boolean SUCCESS = true;
    public static final Boolean FAILED = false;

}
