package uet.ppvan.mangareader.dto;

public record ObjectResponse
    (
        Boolean success,
        String message,
        Object data
    ) {
    public static final Boolean SUCCESS = true;
    public static final Boolean FAILED = false;

}
